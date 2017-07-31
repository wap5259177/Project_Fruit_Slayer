package cn.bonoon.core;

import org.springframework.web.multipart.MultipartFile;

public interface IRequireReportEditor {
	MultipartFile getReportAnnex();
	
	Boolean getDeleteOldAnnex();
}
