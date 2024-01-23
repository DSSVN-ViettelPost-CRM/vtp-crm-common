package vtp.crm.common.utils;

public class Constants {
	public static final String API_VERSION = "v1/";

	// Paging
	public static final String DEFAULT_PAGE = "0";

	public static final int DEFAULT_SIZE = 100;

	public static final String DEFAULT_SORT_KEY = null;

	public static final int PAGE_NUM = 1;

	public static final String PAGE_SORT_ASC = "ASC";

	public static final String PAGE_SORT_DESC = "DESC";

	public static final Integer DEFAULT_UNPAGE = -1;

	// Locale
	public static final String DEFAULT_LOCALE = "vi";

	public static final String DEFAULT_ENCODING = "UTF-8";

	public static final String DEFAULT_BASENAME = "i18n/messages";

	/** Default = DEFAU */
	public static final String SHOWLAYOUT_Default = "DEFAU";
	/** Same On Top = ONTOP */
	public static final String SHOWLAYOUT_SameOnTop = "ONTOP";
	/** Left And Right = LANDR */
	public static final String SHOWLAYOUT_LeftAndRight = "LANDR";

	public static final String ISTOOLBARBUTTON_Window = "N";

	public static final String PROPERTIE_LANGUAGE = "#AD_Language";

	public static final String PROPERTIE_LANG_DEFAULT = "vi_VN";

	/* Format TIME */
	public static final String ISO_MONTH_SDF_PATTERN = "yyyy-MM";

	public static final String MONTH_SDF_PATTERN = "MM-yyyy";

	public static final String DAY_SDF_PATTERN = "dd-MM-yyyy";

	public static final String ISO_DATE_SDF_PATTERN = "yyyy-MM-dd";

	public static final String ISO_TIME_SDF_PATTERN = "yyyy-MM-dd'T'HH:mm:ss";

	public static final String ISO_TIME_TZ_SDF_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

	public static final String FORMAT_DATE_TIME = "dd/MM/yyyy HH:mm:ss";

	public static final String FORMAT_DATE_TIME_REPORT = "dd-MM-yyyy HH:mm";

	public static final String FORMAT_DATE_IMPORT = "dd-MM-yyyy";

	public static final String FORMAT_DATE_TIME_MAIL = "HH:mm - dd-MM-yyyy";

	public static final String FORMAT_DATE_TIME_MOBILE = "dd-MM-yyyy HH:mm:ss";

	public static final String FORMAT_DATE_MOBILE = "dd-MM-yyyy";

	public static final String FORMAT_DATE_CDR = "yyyy-MM-dd'-'HH:mm:ss";

	/* Status */
	public static final String STATUS_YES = "Y";

	public static final String STATUS_NO = "N";

	public static final String EMAIL_PATTERN = ".*@gmail\\.com$";

	public static final String GMAIL_PATTERN = "^[A-Za-z0-9._%+-]+@gmail\\.com$";

	public static final String TOKEN_TYPE = "Bearer ";

	public static final String EMAIL = "Email";

	public static final String SMS = "SMS";

	public static final String IS_NOT_DELETED = "isdeleted=false";

	/* Sample value */
	public static final int SAMPLE_CREATEDBY = 0;

	public static final int SAMPLE_UPDATEDBY = 0;

	public static final boolean STATUS_TRUE = true;

	public static final boolean STATUS_FALSE = false;

	public static final int DYNAMICFIELD_FIELDTYPE_MIN = 1;

	public static final int DYNAMICFIELD_FIELDTYPE_MAX = 3;

	/* SMS */

	public static final String REDIS_KEY_DELIMITER = ".";

	public static final String DEFAULT_CACHE_KEY_GENERATOR = "customCacheKeyGenerator";

	/**
	 * The id can be used if there are no valid id found in the other microservices
	 */
	public static final Long CAN_NOT_FOUND_ID = -1L;

	public static class Organization {

        public static final Long rootOrgId = 1L;

		// not contains white space
		public static final String VALUE_PATTERN = "^((?! ).)*$";
	}

	public static class MasterData {
		public static final String VALUE_PATTERN = "[a-zA-Z0-9]+";
	}

	public static class NotificationType {
		public static final String NUMBER_PATTERN = "[1-9]+";
	}

	/* Date */
	public static final String MIN_BIRTH_DATE = "01/01/1930";

	/* CMND/CCCD */
	public static final String MIN_CARD_DATE = "01/01/1900";
}
