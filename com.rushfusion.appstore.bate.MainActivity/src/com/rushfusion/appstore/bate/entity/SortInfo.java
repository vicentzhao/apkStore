package com.rushfusion.appstore.bate.entity;

public class SortInfo {
	private String name, parent, description, tid;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public SortInfo(String name, String parent, String description, String tid) {
		super();
		this.name = name;
		this.parent = parent;
		this.description = description;
		this.tid = tid;
	}

	public SortInfo() {
		super();
	}
}
