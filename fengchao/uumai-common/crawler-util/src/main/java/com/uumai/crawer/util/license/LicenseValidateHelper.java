package com.uumai.crawer.util.license;

/**
 * Created by rock on 12/23/15.
 */

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import com.uumai.crawer.util.UumaiProperties;
import com.verhas.licensor.License;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class LicenseValidateHelper {

    private static final String license_file= UumaiProperties.getUUmaiHome() + "/licenses/uumai.license";
    private static final String pubring_file= UumaiProperties.getUUmaiHome() + "/licenses/pubring.gpg";

    public LicenseValidateHelper(){

    }

    public LicenseInfo validate() throws Exception{

            File licenseFile = new File(license_file);
            if(!licenseFile.exists())
                throw new Exception("didn't detect license file from:"+license_file);
            File pubkeyFile = new File(pubring_file);
            if(!pubkeyFile.exists())
                throw new Exception("didn't detect public key file from:"+pubring_file);

            // licence 文件验证
            License license = new License();

            if (license
                    .loadKeyRing(pubring_file, null)
                    .setLicenseEncodedFromFile(license_file).isVerified()) {
//                System.out.println(license.getFeature("edition"));
//                System.out.println(license.getFeature("valid-until"));
//                System.out.println(license.getFeature("distribute-pi-count"));
                LicenseInfo licenseInfo=new LicenseInfo();
                licenseInfo.setUser(license.getFeature("user"));
                licenseInfo.setCompany(license.getFeature("company"));
                licenseInfo.setEmail(license.getFeature("email"));
                licenseInfo.setEdition(license.getFeature("edition"));
                licenseInfo.setRelease_version(license.getFeature("release-version"));
                licenseInfo.setValid_until(license.getFeature("valid-until"));
                licenseInfo.setDistribute_pi_count(license.getFeature("distribute-pi-count"));

                validateInfo(licenseInfo);

                return licenseInfo;
            }

          throw new Exception("unknown license check error! quit...");
    }

    private void validateInfo(LicenseInfo licenseInfo) throws Exception{
        //just check validate time
        DateTime in = new DateTime();

        DateTimeFormatter joda_fmt = DateTimeFormat.forPattern("yyyy.MM.dd");//自定义日期格式
        DateTime license_time= joda_fmt.parseDateTime(licenseInfo.getValid_until());

        if(!in.isBefore(license_time))
            throw new Exception("license has expiried. please contact uumai!");

    }

    public static void main(String[] args) throws Exception{
        LicenseValidateHelper licenseValidateHelper=new LicenseValidateHelper();
        licenseValidateHelper.validate();
    }
}
