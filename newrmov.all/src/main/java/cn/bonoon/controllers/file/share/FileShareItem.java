package cn.bonoon.controllers.file.share;

import cn.bonoon.controllers.account.AccountItem;

public class FileShareItem extends AccountItem {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5041832547164646548L;

	private String optDetail;
	private String optDownload;
	private String optEditor;
	private String optDelete;

	public String getOptDetail() {
		return optDetail;
	}

	public void setOptDetail(String optDetail) {
		this.optDetail = optDetail;
	}

	public String getOptDownload() {
		return optDownload;
	}

	public void setOptDownload(String optDownload) {
		this.optDownload = optDownload;
	}

	public String getOptEditor() {
		return optEditor;
	}

	public void setOptEditor(String optEditor) {
		this.optEditor = optEditor;
	}

	public String getOptDelete() {
		return optDelete;
	}

	public void setOptDelete(String optDelete) {
		this.optDelete = optDelete;
	}

}
