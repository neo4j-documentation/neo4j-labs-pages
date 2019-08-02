@Grab('org.jsoup:jsoup:1.12.1')

import org.codehaus.groovy.runtime.EncodingGroovyMethods
import groovy.json.JsonSlurper
import groovy.json.JsonOutput
import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

def success=updatePages("build/docs/html5/",[apoc:99211,"grandstack-graphql":98338,kafka:98345,halin:98347,"etl-tool":98616,"graph-algorithms":98169])
println("Success $success")

def updatePages(directory, pages) {
user=System.getenv('PUBLISH_DOCS_USERNAME')
pass=System.getenv('PUBLISH_DOCS_PASSWORD')
if (!user?.trim() || !pass?.trim()) return false

def auth = EncodingGroovyMethods.encodeBase64("${user}:${pass}".bytes)
def success = true

pages.each { name, pageId -> 

	def file = new File(directory, "${name}.html")
	
	def slurper = new JsonSlurper()
	def url = new URL("https://neo4j.com/wp-json/wp/v2/pages/${pageId}")
	url.openConnection().with {
	  setRequestProperty('accept','application/json')
	  setRequestProperty('content-type','application/json')
	  setRequestProperty('Authorization','Basic '+auth)
	  println("read: ${name} ${getResponseCode()}")
	  if (getResponseCode() == 200) {
	     json = slurper.parseText(content.text)
	     // println json.keySet()
	     update = Jsoup.parse(file, "UTF-8", "https://neo4j.com/labs/${name}")
	                      .select("div.sect1 div.sectionbody").html()
	     // println(update)
	     json['content'] = update;
	     url.openConnection().with {
	        doOutput = true
	        requestMethod = 'POST'
	        setRequestProperty('accept','application/json')
	        setRequestProperty('content-type','application/json')
	        setRequestProperty('Authorization','Basic '+auth)
	        outputStream.withWriter { writer -> writer << JsonOutput.toJson(json) }
	        println("write: ${name} ${getResponseCode()}")
	        success = success && getResponseCode() == 200
	     }
	  }
	}
}
return success
}
