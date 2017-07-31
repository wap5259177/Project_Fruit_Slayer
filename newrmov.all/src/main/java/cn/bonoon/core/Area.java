package cn.bonoon.core;

@Deprecated
public enum Area {
	RURAL(0, "主体村"), Peripheral(1, "非主体村"), PROJECT(2, "项目");

	private int id;
	private String name;

	private Area(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

}
