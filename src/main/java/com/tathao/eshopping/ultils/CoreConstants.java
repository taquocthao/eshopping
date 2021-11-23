package com.tathao.eshopping.ultils;

public class CoreConstants {

	public static final String FORM_MODEL_KEY = "item";
	public static final String FORM_MODEL_KEYS = "items";
	public static final String SORT_ASC = "2";
	public static final String SORT_DESC = "1";
	public static final Integer MAX_PAGE_ITEMS = 20;
	public static final Integer TEN_ITEMS = 10;
	public static final String FORM_ACTION_ADD = "add";
	public static final String FORM_ACTION_EDIT = "edit";
	public static final String FORM_ACTION_DELETE = "delete";
	public static final String FORM_ACTION_ACTIVE = "active";
	public static final String FORM_ACTION_DEACTIVE = "deactive";
	public static final String ALTER = "alert";
	public static final String TYPE_SUCCESS = "success";
	public static final String TYPE_DANGER = "danger";
	public static final String MESSAGE_RESPONSE = "messageResponse";

	public enum UserGroup {
		ADMIN("ADMIN"),
		SHOPPER("SHOPPER"),
		OUTLET("OUTLET"),
		EMPLOYEE("EMPLOYEE");

		private String value;

		UserGroup(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
	}

	public enum Role {
		ADMIN("ADMIN"),
		SHOPPER("SHOPPER"),
		EMPLOYEE("EMPLOYEE");

		private String value;
		Role(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
	}

}
