package cn.bonoon.core;

import java.util.Date;

public interface ParamenterValue {
	double getDouble(Long id, String pre);
	int getInt(Long id, String pre);
	boolean getboolean(Long id,String pre);
	String getString(Long id,String pre);
	String[] getStrings(Long id,String pre);
	Date getDate(Long id,String pre);
}
