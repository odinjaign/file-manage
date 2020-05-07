package com.example.demo1.poi;


import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.URLUtil;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.PicturesManager;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.usermodel.PictureType;
import org.apache.poi.xwpf.converter.core.BasicURIResolver;
import org.apache.poi.xwpf.converter.core.FileImageExtractor;
import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;
import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;


public class Word2Html {
 
	private final static String  tempPath = "C:/Users/jaign/test/img/";
	private final static String imgurlPre = "http://localhost:8080/image/img?path=";

	/**
	 * doc转换为html
	 */
	public static void doc2Html(String fileName, String outPutFile) throws IOException, TransformerException, ParserConfigurationException {
		InputStream input = new FileInputStream(fileName);
		HWPFDocument wordDocument = new HWPFDocument(input);
		Document newDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
		WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(newDocument);
		String imgTempFolder = tempPath + "temp/";
		FileUtil.mkdir(imgTempFolder);
		wordToHtmlConverter.setPicturesManager(new PicturesManager() {
			public String savePicture(byte[] bytes, PictureType pictureType, String name, float v, float v1) {

				File img = new File(imgTempFolder, name);
				try {
					FileOutputStream stream = new FileOutputStream(img);
					stream.write(bytes);
					stream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return URLUtil.encode(img.getPath());
			}
		});
		wordToHtmlConverter.processDocument(wordDocument);
		Document document = wordToHtmlConverter.getDocument();
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		DOMSource domSource = new DOMSource(document);
		StreamResult streamResult = new StreamResult(outStream);
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer serializer = tf.newTransformer();
		serializer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
		serializer.setOutputProperty(OutputKeys.INDENT, "yes");
		serializer.setOutputProperty(OutputKeys.METHOD, "html");
		serializer.transform(domSource, streamResult);
		outStream.close();
		String content = new String(outStream.toByteArray());
		content = content.replace("<img src=\"", "<img src=\"" + imgurlPre + "");
		FileUtil.writeString(content,new File(outPutFile),"utf-8");
	}
 
	/**
	 * 写文件
	 * 
	 * @param content
	 * @param path
	 */
	public static void writeFile(String content, String path) {
		FileOutputStream fos = null;
		BufferedWriter bw = null;
		try {
			File file = new File(path);
			fos = new FileOutputStream(file);
			bw = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"));
			bw.write(content);
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			try {
				if (bw != null)
					bw.close();
				if (fos != null)
					fos.close();
			} catch (IOException ie) {
			}
		}
	}
 
	/**
	 * docx格式word转换为html
	 * 
	 * @param fileName
	 *            docx文件路径
	 * @param outPutFile
	 *            html输出文件路径
	 * @throws TransformerException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	public static void docx2Html(String fileName, String outPutFile) throws TransformerException, IOException, ParserConfigurationException {
		String fileOutName = outPutFile;
		long startTime = System.currentTimeMillis();
		XWPFDocument document = new XWPFDocument(new FileInputStream(fileName));
		XHTMLOptions options = XHTMLOptions.create().indent(4);
		// 导出图片
		File imageFolder = new File(tempPath);
		options.setExtractor(new FileImageExtractor(imageFolder));
		// URI resolver
		//options.URIResolver(new FileURIResolver(imageFolder));
		//替换URL
		options.URIResolver(new BasicURIResolver(imgurlPre+ URLUtil.encode(imageFolder.getPath()+File.separator)));
		File outFile = new File(fileOutName);
		outFile.getParentFile().mkdirs();
		OutputStream out = new FileOutputStream(outFile);
		XHTMLConverter.getInstance().convert(document, out, options);
		System.out.println("Generate " + fileOutName + " with " + (System.currentTimeMillis() - startTime) + " ms.");
 
	}
 
}