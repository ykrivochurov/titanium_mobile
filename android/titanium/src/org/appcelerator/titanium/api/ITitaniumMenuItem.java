package org.appcelerator.titanium.api;

public interface ITitaniumMenuItem
{
	public boolean isRoot();
	public boolean isSeparator();
	public boolean isItem();
	public boolean isSubMenu();

	public boolean isEnabled();

	public ITitaniumMenuItem addSeparator();
	public ITitaniumMenuItem addItem(String label, String callback, String iconUrl);
	public ITitaniumMenuItem addSubMenu(String label, String iconUrl);

	public void enable();
	public void disable();

	public void setLabel(String label);
	public String getLabel();
	public void setIcon(String iconUrl);
	public String getIcon();
	public void setCallback(String callback);
	public String getCallback();
}
