package vtp.crm.common.utils;

public class PermissionCode {

    public static class Customer {

        public static final String CUSTOMER_ALL_DATA_VIEW = "customer_all_data_view";

        public static final String CUSTOMER_ALL_DATA_ADD = "customer_all_data_add";

        public static final String CUSTOMER_OWNER_DATA_VIEW = "customer_owner_data_view";

        public static final String CUSTOMER_OWNER_DATA_ADD = "customer_owner_data_add";

    }

    public static class Dashboard {

        /**
         * Xem dashboad theo quyền quản lý
         */
        public static final String DASHBOARD_AS_MANAGER = "dashboard_by_management_view";

    }

    public static class Campaign {

        /**
         * permission lanh dao. permission xem menu giao nhiem vu
         */
        public static final String MENU_ASSIGN_VIEW = "campaign_list_manager_view";

        public static final String MENU_ASSIGN_ADD = "campaign_list_manager_add";

    }

}
