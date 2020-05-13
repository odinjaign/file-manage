package com.example.demo1.poi;

import cn.hutool.core.io.FileUtil;
import com.example.demo1.entity.MarkdownEntity;
import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.parser.ParserEmulationProfile;
import com.vladsch.flexmark.util.options.MutableDataSet;

import java.nio.charset.StandardCharsets;
import java.util.Collections;

public class Md2Html {

    public static String mdToHtmlByContent(String content){
        // markdown to image
        MutableDataSet options = new MutableDataSet();

        options.setFrom(ParserEmulationProfile.MARKDOWN);
        options.set(Parser.EXTENSIONS, Collections.singletonList(TablesExtension.create()));

        Parser parser = Parser.builder(options).build();
        HtmlRenderer renderer = HtmlRenderer.builder(options).build();

        Node document = parser.parse(content);
        String html = renderer.render(document);

        String css = ".margin-tb-zero,\n" +
                ".markdown-body ol ol,\n" +
                ".markdown-body ul ol,\n" +
                ".markdown-body ol ul,\n" +
                ".markdown-body ul ul,\n" +
                ".markdown-body ol ul ol,\n" +
                ".markdown-body ul ul ol,\n" +
                ".markdown-body ol ul ul,\n" +
                ".markdown-body ul ul ul {\n" +
                "  margin-top: 0;\n" +
                "  margin-bottom: 0;\n" +
                "}\n" +
                ".markdown-body {\n" +
                "  font-family: \"Helvetica Neue\", Helvetica, \"Segoe UI\", Arial, freesans, sans-serif, \"Apple Color Emoji\", \"Segoe UI Emoji\", \"Segoe UI Symbol\";\n" +
                "  font-size: 16px;\n" +
                "  color: #333;\n" +
                "  line-height: 1.6;\n" +
                "  word-wrap: break-word;\n" +
                "  padding: 45px;\n" +
                "  background: #fff;\n" +
                "  border: 1px solid #ddd;\n" +
                "  -webkit-border-radius: 0 0 3px 3px;\n" +
                "  border-radius: 0 0 3px 3px;\n" +
                "}\n" +
                ".markdown-body > *:first-child {\n" +
                "  margin-top: 0 !important;\n" +
                "}\n" +
                ".markdown-body > *:last-child {\n" +
                "  margin-bottom: 0 !important;\n" +
                "}\n" +
                ".markdown-body * {\n" +
                "  -webkit-box-sizing: border-box;\n" +
                "  -moz-box-sizing: border-box;\n" +
                "  box-sizing: border-box;\n" +
                "}\n" +
                ".markdown-body h1,\n" +
                ".markdown-body h2,\n" +
                ".markdown-body h3,\n" +
                ".markdown-body h4,\n" +
                ".markdown-body h5,\n" +
                ".markdown-body h6 {\n" +
                "  margin-top: 1em;\n" +
                "  margin-bottom: 16px;\n" +
                "  font-weight: bold;\n" +
                "  line-height: 1.4;\n" +
                "}\n" +
                ".markdown-body p,\n" +
                ".markdown-body blockquote,\n" +
                ".markdown-body ul,\n" +
                ".markdown-body ol,\n" +
                ".markdown-body dl,\n" +
                ".markdown-body table,\n" +
                ".markdown-body pre {\n" +
                "  margin-top: 0;\n" +
                "  margin-bottom: 16px;\n" +
                "}\n" +
                ".markdown-body h1 {\n" +
                "  margin: 0.67em 0;\n" +
                "  padding-bottom: 0.3em;\n" +
                "  font-size: 2.25em;\n" +
                "  line-height: 1.2;\n" +
                "  border-bottom: 1px solid #eee;\n" +
                "}\n" +
                ".markdown-body h2 {\n" +
                "  padding-bottom: 0.3em;\n" +
                "  font-size: 1.75em;\n" +
                "  line-height: 1.225;\n" +
                "  border-bottom: 1px solid #eee;\n" +
                "}\n" +
                ".markdown-body h3 {\n" +
                "  font-size: 1.5em;\n" +
                "  line-height: 1.43;\n" +
                "}\n" +
                ".markdown-body h4 {\n" +
                "  font-size: 1.25em;\n" +
                "}\n" +
                ".markdown-body h5 {\n" +
                "  font-size: 1em;\n" +
                "}\n" +
                ".markdown-body h6 {\n" +
                "  font-size: 1em;\n" +
                "  color: #777;\n" +
                "}\n" +
                ".markdown-body ol,\n" +
                ".markdown-body ul {\n" +
                "  padding-left: 2em;\n" +
                "}\n" +
                ".markdown-body ol ol,\n" +
                ".markdown-body ul ol {\n" +
                "  list-style-type: lower-roman;\n" +
                "}\n" +
                ".markdown-body ol ul,\n" +
                ".markdown-body ul ul {\n" +
                "  list-style-type: circle;\n" +
                "}\n" +
                ".markdown-body ol ul ul,\n" +
                ".markdown-body ul ul ul {\n" +
                "  list-style-type: square;\n" +
                "}\n" +
                ".markdown-body ol {\n" +
                "  list-style-type: decimal;\n" +
                "}\n" +
                ".markdown-body ul {\n" +
                "  list-style-type: disc;\n" +
                "}\n" +
                ".markdown-body blockquote {\n" +
                "  margin-left: 0;\n" +
                "  margin-right: 0;\n" +
                "  padding: 0 15px;\n" +
                "  color: #777;\n" +
                "  border-left: 4px solid #ddd;\n" +
                "}\n" +
                ".markdown-body table {\n" +
                "  display: block;\n" +
                "  width: 100%;\n" +
                "  overflow: auto;\n" +
                "  word-break: normal;\n" +
                "  word-break: keep-all;\n" +
                "  border-collapse: collapse;\n" +
                "  border-spacing: 0;\n" +
                "}\n" +
                ".markdown-body table tr {\n" +
                "  background-color: #fff;\n" +
                "  border-top: 1px solid #ccc;\n" +
                "}\n" +
                ".markdown-body table tr:nth-child(2n) {\n" +
                "  background-color: #f8f8f8;\n" +
                "}\n" +
                ".markdown-body table th,\n" +
                ".markdown-body table td {\n" +
                "  padding: 6px 13px;\n" +
                "  border: 1px solid #ddd;\n" +
                "}\n" +
                ".markdown-body pre {\n" +
                "  word-wrap: normal;\n" +
                "  padding: 16px;\n" +
                "  overflow: auto;\n" +
                "  font-size: 85%;\n" +
                "  line-height: 1.45;\n" +
                "  background-color: #f7f7f7;\n" +
                "  -webkit-border-radius: 3px;\n" +
                "  border-radius: 3px;\n" +
                "}\n" +
                ".markdown-body pre code {\n" +
                "  display: inline;\n" +
                "  max-width: initial;\n" +
                "  padding: 0;\n" +
                "  margin: 0;\n" +
                "  overflow: initial;\n" +
                "  font-size: 100%;\n" +
                "  line-height: inherit;\n" +
                "  word-wrap: normal;\n" +
                "  white-space: pre;\n" +
                "  border: 0;\n" +
                "  -webkit-border-radius: 3px;\n" +
                "  border-radius: 3px;\n" +
                "  background-color: transparent;\n" +
                "}\n" +
                ".markdown-body pre code:before,\n" +
                ".markdown-body pre code:after {\n" +
                "  content: normal;\n" +
                "}\n" +
                ".markdown-body code {\n" +
                "  font-family: Consolas, \"Liberation Mono\", Menlo, Courier, monospace;\n" +
                "  padding: 0;\n" +
                "  padding-top: 0.2em;\n" +
                "  padding-bottom: 0.2em;\n" +
                "  margin: 0;\n" +
                "  font-size: 85%;\n" +
                "  background-color: rgba(0,0,0,0.04);\n" +
                "  -webkit-border-radius: 3px;\n" +
                "  border-radius: 3px;\n" +
                "}\n" +
                ".markdown-body code:before,\n" +
                ".markdown-body code:after {\n" +
                "  letter-spacing: -0.2em;\n" +
                "  content: \"\\00a0\";\n" +
                "}\n" +
                ".markdown-body a {\n" +
                "  color: #4078c0;\n" +
                "  text-decoration: none;\n" +
                "  background: transparent;\n" +
                "}\n" +
                ".markdown-body img {\n" +
                "  max-width: 100%;\n" +
                "  max-height: 100%;\n" +
                "  -webkit-border-radius: 4px;\n" +
                "  border-radius: 4px;\n" +
                "  -webkit-box-shadow: 0 0 10px #555;\n" +
                "  box-shadow: 0 0 10px #555;\n" +
                "}\n" +
                ".markdown-body strong {\n" +
                "  font-weight: bold;\n" +
                "}\n" +
                ".markdown-body em {\n" +
                "  font-style: italic;\n" +
                "}\n" +
                ".markdown-body del {\n" +
                "  text-decoration: line-through;\n" +
                "}\n" +
                ".task-list-item {\n" +
                "  list-style-type: none;\n" +
                "}\n" +
                ".task-list-item input {\n" +
                "  font: 13px/1.4 Helvetica, arial, nimbussansl, liberationsans, freesans, clean, sans-serif, \"Apple Color Emoji\", \"Segoe UI Emoji\", \"Segoe UI Symbol\";\n" +
                "  margin: 0 0.35em 0.25em -1.6em;\n" +
                "  vertical-align: middle;\n" +
                "}\n" +
                ".task-list-item input[disabled] {\n" +
                "  cursor: default;\n" +
                "}\n" +
                ".task-list-item input[type=\"checkbox\"] {\n" +
                "  -webkit-box-sizing: border-box;\n" +
                "  -moz-box-sizing: border-box;\n" +
                "  box-sizing: border-box;\n" +
                "  padding: 0;\n" +
                "}\n" +
                ".task-list-item input[type=\"radio\"] {\n" +
                "  -webkit-box-sizing: border-box;\n" +
                "  -moz-box-sizing: border-box;\n" +
                "  box-sizing: border-box;\n" +
                "  padding: 0;\n" +
                "}\n";
        css = "<style type=\"text/css\">\n" + css + "\n</style>\n";
        MarkdownEntity entity = new MarkdownEntity();
        entity.setCss(css);
        entity.setHtml(html);
        entity.addDivStyle("class", "markdown-body ");
        return entity.toString();
    }

    public static String mdToHtmlByFile(String filepath){
        String content = FileUtil.readString(filepath, StandardCharsets.UTF_8);
        return mdToHtmlByContent(content);
    }
}
