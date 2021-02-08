package com.spring.common.util.excel;

import java.io.Serializable;

/**
* @author 作者：赵进华 
* @version 创建时间：2019年3月21日 上午9:36:54
* @Desc类说明:文件模型
*/
public class FileModel implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7546464263707048203L;
	private String filename;
	private String filetype;
	private Long filesize;
	private String filepath;
	private String fileurl;
	
	public FileModel(){
		super();
	}
	public FileModel(String filename, String filetype, Long filesize,
			String filepath,String fileurl){
		super();
		this.filename = filename.trim().replaceAll(" ","");
		this.filetype = filetype;
		this.filesize = filesize;
		this.filepath = filepath;
		this.fileurl = fileurl;
	}
	public String getFilename(){
		return filename;
	}
	public void setFilename(String filename){
		this.filename = filename;
	}
	public String getFiletype(){
		return filetype;
	}
	public void setFiletype(String filetype){
		this.filetype = filetype;
	}
	public Long getFilesize(){
		return filesize;
	}
	public void setFilesize(Long filesize){
		this.filesize = filesize;
	}
	public String getFilepath(){
		return filepath;
	}
	public void setFilepath(String filepath){
		this.filepath = filepath;
	}
	public String getFileurl() {
		return fileurl;
	}
	public void setFileurl(String fileurl) {
		this.fileurl = fileurl;
	}
}