package com.sand.updiff.common;

/**
 *
 * @author : sun.mt
 * @date : 2015/8/3 18:50
 * @since 1.0.0
 *
 */
public class DiffItem {

	private String groupName;

	private String changeName;

	private boolean isExcluded;

	private String path;

	private String packaging;

	private String mainJavaGroup;

	private String[] mainResourceGroups;

	public DiffItem (
			String groupName,
			String changeName,
			boolean isExcluded,
			String path) {
		this.groupName = groupName;
		this.changeName = changeName;
		this.isExcluded = isExcluded;
		this.path = path;
	}

	public DiffItem (
			String groupName,
			String changeName,
			boolean isExcluded,
			String path,
			String packaging,
			String mainJavaGroup,
			String[] mainResourceGroups) {
		this.groupName = groupName;
		this.changeName = changeName;
		this.isExcluded = isExcluded;
		this.path = path;
		this.packaging = packaging;
		this.mainJavaGroup = mainJavaGroup;
		this.mainResourceGroups = mainResourceGroups;
	}

	public String getChangeName () {
		return changeName;
	}

	public String getPath () {
		return path;
	}


	public String getGroupName () {
		return groupName;
	}

	public boolean isExcluded () {
		return isExcluded;
	}

	public String getCompiledNewPath(){

		String subfix = ".java";
		if(path.endsWith(subfix)){
			return path.substring(0, path.length() - subfix.length()) + ".class";
		}
		return path;
	}

	public void setGroupName (String groupName) {
		this.groupName = groupName;
	}

	@Override
	public String toString () {
		StringBuilder sb = new StringBuilder("[");

		sb.append("groupName: ").append(groupName).append(", ");
		sb.append("changeName: ").append(changeName).append(", ");
		sb.append("isExcluded: ").append(isExcluded).append(", ");
		sb.append("path: ").append(path);

		return sb.append("]").toString();
	}

	public String getPackaging () {
		return packaging;
	}

	public String getMainJavaGroup () {
		return mainJavaGroup;
	}

	public String[] getMainResourceGroups () {
		return mainResourceGroups;
	}
}
