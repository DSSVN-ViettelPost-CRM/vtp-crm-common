package vtp.crm.common.vo;

import java.util.List;

public interface BuildableTreeVO<T> {

	T getId();

	T getParentId();

	List<BuildableTreeVO<T>> getChildren();

	void setChildren(List<BuildableTreeVO<T>> children);

}
