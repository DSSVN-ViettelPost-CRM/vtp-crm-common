# vtp-crm-common

---
# 

1. thêm cấu hình vào file pom.xml
```xml
    <distributionManagement>
        <repository>
            <id>github</id>
            <name>GitHub VTP Apache Maven Packages</name>
            <url>https://maven.pkg.github.com/DSSVN-ViettelPost-CRM/vtp-crm-common</url>
        </repository>
    </distributionManagement>
```

2. cấu hình cicd như file [file](.github/workflows/github-package.yml)

3. cách sử dụng :
- tạo 1 release giả sử `1.0.0`
- tại project cần sử dụng sẽ thêm cấu hình :
    - jitpack:
```xml
<project>
  <repositories>
    <repository>
      <id>jitpack.io</id>
      <url>https://jitpack.io</url>
    </repository>
  </repositories>

  <dependencies>
    <dependency>
      <groupId>com.github.DSSVN-ViettelPost-CRM</groupId>
      <artifactId>vtp-crm-common</artifactId>
      <version>1.0.0</version>
    </dependency>
  </dependencies>
</project>

```

- `1.0.0` là tên của bản release.
- nếu code của package được update thì chỉ cần cập nhật version của dependency là được.
- Nếu dự án không có bất kỳ Bản phát hành GitHub nào, có thể sử dụng `develop-SNAPSHOT` làm phiên bản.
