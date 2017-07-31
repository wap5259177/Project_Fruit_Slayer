package cn.bonoon.controllers.file;

import cn.bonoon.entities.DirectoryEntity;
import cn.bonoon.kernel.support.models.Node;

public class DirectoryNode extends Node{

	public DirectoryNode(DirectoryEntity entity, Long pid){
		super(entity.getId(), entity.getName(), pid);
		share = entity.getShare();
		disabled = entity.getStatus() == -1;
	}
	
	public DirectoryNode(String name){
		super(0, name);
	}
	
	private int share;
	
	private boolean disabled;
	
	public boolean isDisabled() {
		return disabled;
	}
	
	public int getShare() {
		return share;
	}
}
