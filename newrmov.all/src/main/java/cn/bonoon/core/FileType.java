package cn.bonoon.core;

/**
 * 文件类型
 * 
 * @author ocean~
 */
public enum FileType {

	DOC(0, "文档"), IMAGE(1, "图片"), MUSIC(2, "音频"), VIDEO(3, "视频"), OTHER(4, "其它");

	private int type;
	private String name;

	private FileType(int type, String name) {
		this.type = type;
		this.name = name;
	}

	public int getType() {
		return type;
	}

	public String getName() {
		return name;
	}

}
