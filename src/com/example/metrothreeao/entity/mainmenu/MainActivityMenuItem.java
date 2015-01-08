package com.example.metrothreeao.entity.mainmenu;

public class MainActivityMenuItem {
	
	private int id;
	private int icResource;
	private String name;

	public MainActivityMenuItem() {
		super();
	}

	

	public MainActivityMenuItem(int id, int icResource, String name) {
		super();
		this.id = id;
		this.icResource = icResource;
		this.name = name;
	}



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public int getIcResource() {
		return icResource;
	}

	public void setIcResource(int icResource) {
		this.icResource = icResource;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}



	@Override
	public String toString() {
		return "MainActivityMenuItem [id=" + id + ", icResource=" + icResource
				+ ", name=" + name + "]";
	}

	

}
