# frozen_string_literal: true

require 'asciidoctor/extensions' unless RUBY_ENGINE == 'opal'

module Neo4j
  # Asciidoctor extensions by Neo4j
  module AsciidoctorExtensions

    # A tree processor that sets or updates the taxonomies attribute from attributes.
    # Currently we are using the attribute neo4j-versions to set the neo4j_version taxonomy.
    #
    class Neo4jTaxonomiesTreeProcessor < Asciidoctor::Extensions::TreeProcessor
      use_dsl

      def process(document)
        if (neo4j_versions = document.attr('neo4j-versions'))
          value = "#{document.attr?('taxonomies') ? "#{document.attr('taxonomies')}," : ''}neo4j_version=#{neo4j_versions.gsub(',', ';')}"
          document.set_attribute 'taxonomies', value
        end
        document
      end
    end
  end
end

Asciidoctor::Extensions.register do
  tree_processor Neo4j::AsciidoctorExtensions::Neo4jTaxonomiesTreeProcessor
end
