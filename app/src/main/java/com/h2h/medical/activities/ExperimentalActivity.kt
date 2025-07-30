package com.h2h.medical.activities

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.h2h.medical.R
import kotlinx.android.synthetic.main.activity_experimental.*


class ExperimentalActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_experimental)

        rv_test.layoutManager = LinearLayoutManager(this)
    }

//    fun createPassword() {
//        try {
//            //Set connection properties required to invoke AEM Forms using SOAP mode
//            val connectionProps = Properties()
//            connectionProps.setProperty(
//                ServiceClientFactoryProperties.DSC_DEFAULT_SOAP_ENDPOINT,
//                "https://[server]:[port]"
//            )
//            connectionProps.setProperty(
//                ServiceClientFactoryProperties.DSC_TRANSPORT_PROTOCOL,
//                ServiceClientFactoryProperties.DSC_SOAP_PROTOCOL
//            )
//            connectionProps.setProperty(ServiceClientFactoryProperties.DSC_SERVER_TYPE, "JBoss")
//            connectionProps.setProperty(
//                ServiceClientFactoryProperties.DSC_CREDENTIAL_USERNAME,
//                "administrator"
//            )
//            connectionProps.setProperty(
//                ServiceClientFactoryProperties.DSC_CREDENTIAL_PASSWORD,
//                "password"
//            )
//
//            //Create a ServiceClientFactory instance
//            var myFactory = ServiceClientFactory.createInstance(connectionProps)
//
//            //Create an EncryptionServiceClient object
//            var encryptClient = EncryptionServiceClient(myFactory)
//
//            //Specify the PDF document to encrypt with a password
//            var fileInputStream = FileInputStream("")
//            val inDoc = Document(fileInputStream)
//
//            //Create a PasswordEncryptionOptionSpec object that stores encryption run-time values
//            var passSpec = PasswordEncryptionOptionSpec()
//
//            //Specify the PDF document resource to encrypt
//            passSpec.setEncryptOption(PasswordEncryptionOption.ALL)
//
//            //Specify the permission associated with the password
//            //These permissions enable data to be extracted from a password
//            //protected PDF form
//            List<PasswordEncryptionPermission> encrypPermissions = new ArrayList<PasswordEncryptionPermission>();
//            encrypPermissions.add(PasswordEncryptionPermission.PASSWORD_EDIT_ADD);
//            encrypPermissions.add(PasswordEncryptionPermission.PASSWORD_EDIT_MODIFY);
//            passSpec.setPermissionsRequested(encrypPermissions);
//
//            //Specify the Acrobat version
//            passSpec.setCompatability(PasswordEncryptionCompatability.ACRO_7);
//
//            //Specify the password values
////            passSpec.setDocumentOpenPassword("OpenPassword");
////            passSpec.setPermissionPassword("PermissionPassword");
////
////            //Encrypt the PDF document
////            val encryptDoc = encryptClient.encryptPDFUsingPassword(inDoc, passSpec)
//
//            //Save the password-encrypted PDF document
//            var outFile = File("")
////            encryptDoc.copyToFile(outFile);
//
//        } catch (exception: Exception) {
//            exception.printStackTrace()
//        }
//    }
}