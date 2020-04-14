require 'asciidoctor/extensions' unless RUBY_ENGINE == 'opal'

include Asciidoctor

class StageSlugTreeProcessor < Extensions::TreeProcessor; use_dsl

  TESTING_SLUG_PREFIX = '_testing_'

  def process(document)
    if (document_slug = document.attr 'slug')
      if (document.attr 'stage') != 'production'
        document_slug = "#{TESTING_SLUG_PREFIX}#{document_slug}"
        document.set_attr 'slug', document_slug
      end
    end
    document
  end
end

Extensions.register do
  tree_processor StageSlugTreeProcessor
end
