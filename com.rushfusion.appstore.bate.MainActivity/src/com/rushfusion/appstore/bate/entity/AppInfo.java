package com.rushfusion.appstore.bate.entity;

import java.util.List;


public class AppInfo {
	private int comment_count;
	private String id, icon, author, source, description, name, apkfile, version;
	private List<String> screenshots;
	private List<String> types;
	
	public AppInfo() {
		super();
	}

	public AppInfo(int comment_count, String id, String icon, String author,
			String source, List<String> screenshots, String description,
			String name, List<String> types, String apkfile, String version) {
		super();
		this.comment_count = comment_count;
		this.id = id;
		this.icon = icon;
		this.author = author;
		this.source = source;
		this.screenshots = screenshots;
		this.description = description;
		this.name = name;
		this.types = types;
		this.apkfile = apkfile;
		this.version = version;
	}
	
	public int getComment_count() {
		return comment_count;
	}
	public void setComment_count(int comment_count) {
		this.comment_count = comment_count;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public List<String> getScreenshots() {
		return screenshots;
	}
	public void setScreenshots(List<String> screenshots) {
		this.screenshots = screenshots;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<String> getTypes() {
		return types;
	}
	public void setTypes(List<String> types) {
		this.types = types;
	}
	public String getApkfile() {
		return apkfile;
	}
	public void setApkfile(String apkfile) {
		this.apkfile = apkfile;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
}
