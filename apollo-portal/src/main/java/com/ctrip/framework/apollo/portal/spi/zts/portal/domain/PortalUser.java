package com.ctrip.framework.apollo.portal.spi.zts.portal.domain;

import java.util.List;

/**
 * @author roc
 * @since 2020/6/10 14:03
 */
public class PortalUser {
    private String userName;
    private String name;
    private String email;
    private String phoneNo;
    private String officePhone;
    private String gender;
    private String status;
    private String chgPassFlag;
    private String orgs;
    private String employeeNumber;
    private String leaveDate;
    private int type;
    private String jobCode;
    private String jobName;
    private String jobStatus;
    private String jobGroupCode;
    private String jobGroupName;
    private String empStatus;
    private String workPlace;
    private Object phoneShortNo;
    private Object officeShortNo;
    private String datasource;
    private Links links;
    private int id;
    private List<UserMaps> userMaps;
    private List<PartOrgs> partOrgs;
    private List<UserProperties> properties;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getOfficePhone() {
        return officePhone;
    }

    public void setOfficePhone(String officePhone) {
        this.officePhone = officePhone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getChgPassFlag() {
        return chgPassFlag;
    }

    public void setChgPassFlag(String chgPassFlag) {
        this.chgPassFlag = chgPassFlag;
    }

    public String getOrgs() {
        return orgs;
    }

    public void setOrgs(String orgs) {
        this.orgs = orgs;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public String getLeaveDate() {
        return leaveDate;
    }

    public void setLeaveDate(String leaveDate) {
        this.leaveDate = leaveDate;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getJobCode() {
        return jobCode;
    }

    public void setJobCode(String jobCode) {
        this.jobCode = jobCode;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(String jobStatus) {
        this.jobStatus = jobStatus;
    }

    public String getJobGroupCode() {
        return jobGroupCode;
    }

    public void setJobGroupCode(String jobGroupCode) {
        this.jobGroupCode = jobGroupCode;
    }

    public String getJobGroupName() {
        return jobGroupName;
    }

    public void setJobGroupName(String jobGroupName) {
        this.jobGroupName = jobGroupName;
    }

    public String getEmpStatus() {
        return empStatus;
    }

    public void setEmpStatus(String empStatus) {
        this.empStatus = empStatus;
    }

    public String getWorkPlace() {
        return workPlace;
    }

    public void setWorkPlace(String workPlace) {
        this.workPlace = workPlace;
    }

    public Object getPhoneShortNo() {
        return phoneShortNo;
    }

    public void setPhoneShortNo(Object phoneShortNo) {
        this.phoneShortNo = phoneShortNo;
    }

    public Object getOfficeShortNo() {
        return officeShortNo;
    }

    public void setOfficeShortNo(Object officeShortNo) {
        this.officeShortNo = officeShortNo;
    }

    public String getDatasource() {
        return datasource;
    }

    public void setDatasource(String datasource) {
        this.datasource = datasource;
    }

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<UserMaps> getUserMaps() {
        return userMaps;
    }

    public void setUserMaps(List<UserMaps> userMaps) {
        this.userMaps = userMaps;
    }

    public List<PartOrgs> getPartOrgs() {
        return partOrgs;
    }

    public void setPartOrgs(List<PartOrgs> partOrgs) {
        this.partOrgs = partOrgs;
    }

    public List<UserProperties> getProperties() {
        return properties;
    }

    public void setProperties(List<UserProperties> properties) {
        this.properties = properties;
    }

    public static class Links {
        /**
         * self : {"href":"http://localhost:8080/v1/users/395"}
         */

        private SelfBean self;

        public SelfBean getSelf() {
            return self;
        }

        public void setSelf(SelfBean self) {
            this.self = self;
        }

        public static class SelfBean {
            /**
             * href : http://localhost:8080/v1/users/395
             */

            private String href;

            public String getHref() {
                return href;
            }

            public void setHref(String href) {
                this.href = href;
            }
        }
    }

    public static class UserMaps {
        /**
         * id : 395
         * userId : 395
         * convertId : 7788string
         * appCode : M0001
         * status : 1
         * employeeNumber : 7788string
         */

        private int id;
        private int userId;
        private String convertId;
        private String appCode;
        private String status;
        private String employeeNumber;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getConvertId() {
            return convertId;
        }

        public void setConvertId(String convertId) {
            this.convertId = convertId;
        }

        public String getAppCode() {
            return appCode;
        }

        public void setAppCode(String appCode) {
            this.appCode = appCode;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getEmployeeNumber() {
            return employeeNumber;
        }

        public void setEmployeeNumber(String employeeNumber) {
            this.employeeNumber = employeeNumber;
        }
    }

    public static class PartOrgs {
        /**
         * id : 15
         * groupNumber : 0156
         * jobCode : 301
         * jobName : 分支机构管理岗
         * jobGroupCode : null
         * jobGroupName : 7788string
         */

        private int id;
        private String groupNumber;
        private String jobCode;
        private String jobName;
        private Object jobGroupCode;
        private String jobGroupName;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getGroupNumber() {
            return groupNumber;
        }

        public void setGroupNumber(String groupNumber) {
            this.groupNumber = groupNumber;
        }

        public String getJobCode() {
            return jobCode;
        }

        public void setJobCode(String jobCode) {
            this.jobCode = jobCode;
        }

        public String getJobName() {
            return jobName;
        }

        public void setJobName(String jobName) {
            this.jobName = jobName;
        }

        public Object getJobGroupCode() {
            return jobGroupCode;
        }

        public void setJobGroupCode(Object jobGroupCode) {
            this.jobGroupCode = jobGroupCode;
        }

        public String getJobGroupName() {
            return jobGroupName;
        }

        public void setJobGroupName(String jobGroupName) {
            this.jobGroupName = jobGroupName;
        }
    }

    public static class UserProperties {
        /**
         * id : 3
         * userId : 395
         * userKey : key2
         * userValue : val2
         * employeeNumber : 7788
         */

        private int id;
        private int userId;
        private String userKey;
        private String userValue;
        private String employeeNumber;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getUserKey() {
            return userKey;
        }

        public void setUserKey(String userKey) {
            this.userKey = userKey;
        }

        public String getUserValue() {
            return userValue;
        }

        public void setUserValue(String userValue) {
            this.userValue = userValue;
        }

        public String getEmployeeNumber() {
            return employeeNumber;
        }

        public void setEmployeeNumber(String employeeNumber) {
            this.employeeNumber = employeeNumber;
        }
    }
}
