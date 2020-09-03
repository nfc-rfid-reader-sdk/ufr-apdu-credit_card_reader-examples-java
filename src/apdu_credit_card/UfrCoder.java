
package apdu_credit_card;

import static apdu_credit_card.UfrCoder.GetLibFullPath;

import java.awt.print.Book;

import javax.swing.JOptionPane;

import com.sun.jna.*;
import com.sun.jna.platform.*;
import com.sun.jna.ptr.ByteByReference;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.PointerByReference;
import com.sun.jna.ptr.ShortByReference;

import apdu_credit_card.UfrCoder.uFrFunctions;

public class UfrCoder {
	
	public enum ERRORCODES 
	{
		UFR_OK(0x00),
		UFR_COMMUNICATION_ERROR(0x01),
		UFR_CHKSUM_ERROR(0x02),
		UFR_READING_ERROR(0x03),
		UFR_WRITING_ERROR(0x04),
		UFR_BUFFER_OVERFLOW(0x05),
		UFR_MAX_ADDRESS_EXCEEDED(0x06),
		UFR_MAX_KEY_INDEX_EXCEEDED(0x07),
		UFR_NO_CARD(0x08),
		UFR_COMMAND_NOT_SUPPORTED(0x09),
		UFR_FORBIDEN_DIRECT_WRITE_IN_SECTOR_TRAILER(0x0A),
		UFR_ADDRESSED_BLOCK_IS_NOT_SECTOR_TRAILER(0x0B),
		UFR_WRONG_ADDRESS_MODE(0x0C),
		UFR_WRONG_ACCESS_BITS_VALUES(0x0D),
		UFR_AUTH_ERROR(0x0E),
		UFR_PARAMETERS_ERROR(0x0F),
		UFR_MAX_SIZE_EXCEEDED(0x10),
		UFR_UNSUPPORTED_CARD_TYPE(0x11),

		UFR_COMMUNICATION_BREAK(0x50),
		UFR_NO_MEMORY_ERROR(0x51),
		UFR_CAN_NOT_OPEN_READER(0x52),
		UFR_READER_NOT_SUPPORTED(0x53),
		UFR_READER_OPENING_ERROR(0x54),
		UFR_READER_PORT_NOT_OPENED(0x55),
		UFR_CANT_CLOSE_READER_PORT(0x56),

		UFR_WRITE_VERIFICATION_ERROR(0x70),
		UFR_BUFFER_SIZE_EXCEEDED(0x71),
		UFR_VALUE_BLOCK_INVALID(0x72),
		UFR_VALUE_BLOCK_ADDR_INVALID(0x73),
		UFR_VALUE_BLOCK_MANIPULATION_ERROR(0x74),
		UFR_WRONG_UI_MODE(0x75),
		UFR_KEYS_LOCKED(0x76),
		UFR_KEYS_UNLOCKED(0x77),
		UFR_WRONG_PASSWORD(0x78),
		UFR_CAN_NOT_LOCK_DEVICE(0x79),
		UFR_CAN_NOT_UNLOCK_DEVICE(0x7A),
		UFR_DEVICE_EEPROM_BUSY(0x7B),
		UFR_RTC_SET_ERROR(0x7C),

		ANTI_COLLISION_DISABLED(0x7D),
		NO_TAGS_ENUMERRATED(0x7E),
		CARD_ALREADY_SELECTED(0x7F),

		// NDEF error codes
		UFR_WRONG_NDEF_CARD_FORMAT(0x80),
		UFR_NDEF_MESSAGE_NOT_FOUND(0x81),
		UFR_NDEF_UNSUPPORTED_CARD_TYPE(0x82),
		UFR_NDEF_CARD_FORMAT_ERROR(0x83),
		UFR_MAD_NOT_ENABLED(0x84),
		UFR_MAD_VERSION_NOT_SUPPORTED(0x85),
		UFR_NDEF_MESSAGE_NOT_COMPATIBLE(0x86),

		// Tag emulation mode errors:
		FORBIDDEN_IN_TAG_EMULATION_MODE(0x90),

		// FTDI errors:
		UFR_FT_STATUS_ERROR_1(0xA0),
		UFR_FT_STATUS_ERROR_2(0xA1),
		UFR_FT_STATUS_ERROR_3(0xA2),
		UFR_FT_STATUS_ERROR_4(0xA3),
		UFR_FT_STATUS_ERROR_5(0xA4),
		UFR_FT_STATUS_ERROR_6(0xA5),
		UFR_FT_STATUS_ERROR_7(0xA6),
		UFR_FT_STATUS_ERROR_8(0xA7),
		UFR_FT_STATUS_ERROR_9(0xA8),

		//MIFARE PLUS error codes
		UFR_MFP_COMMAND_OVERFLOW(0xB0),
		UFR_MFP_INVALID_MAC(0xB1),
		UFR_MFP_INVALID_BLOCK_NR(0xB2),
		UFR_MFP_NOT_EXIST_BLOCK_NR(0xB3),
		UFR_MFP_COND_OF_USE_ERROR(0xB4),
		UFR_MFP_LENGTH_ERROR(0xB5),
		UFR_MFP_GENERAL_MANIP_ERROR(0xB6),
		UFR_MFP_SWITCH_TO_ISO14443_4_ERROR(0xB7),
		UFR_MFP_ILLEGAL_STATUS_CODE(0xB8),
		UFR_MFP_MULTI_BLOCKS_READ(0xB9),

		//NT4H error codes
		NT4H_COMMAND_ABORTED (  0xC0),
		NT4H_LENGTH_ERROR(0xC1),
		NT4H_PARAMETER_ERROR(0xC2),
		NT4H_NO_SUCH_KEY(0xC3),
		NT4H_PERMISSION_DENIED(0xC4),
		NT4H_AUTHENTICATION_DELAY(0xC5),
		NT4H_MEMORY_ERROR(0xC6),
		NT4H_INTEGRITY_ERROR(0xC7),
		NT4H_FILE_NOT_FOUND(0xC8),
		NT4H_BOUNDARY_ERROR(0xC9),
		NT4H_INVALID_MAC(0xCA),
		NT4H_NO_CHANGES(0xCB),
					
		// multiple units - return from the functions with ReaderList_ prefix in name
		UFR_DEVICE_WRONG_HANDLE(0x100),
		UFR_DEVICE_INDEX_OUT_OF_BOUND(0x101),
		UFR_DEVICE_ALREADY_OPENED(0x102),
		UFR_DEVICE_ALREADY_CLOSED(0x103),
		UFR_DEVICE_IS_NOT_CONNECTED(0x104),

		// Originality Check Error Codes:
		UFR_NOT_NXP_GENUINE(0x200),
		UFR_OPEN_SSL_DYNAMIC_LIB_FAILED(0x201),
		UFR_OPEN_SSL_DYNAMIC_LIB_NOT_FOUND(0x202),

		// DESFIRE Card Status Error Codes:
		READER_ERROR(0xBB7),					// 2999 [dec]
		NO_CARD_DETECTED(0xBB8),				// 3000 [dec]
		CARD_OPERATION_OK(0xBB9),				// 3001 [dec]
		WRONG_KEY_TYPE(0xBBA),					// 3002 [dec]
		KEY_AUTH_ERROR(0xBBB),					// 3003 [dec]
		CARD_CRYPTO_ERROR(0xBBC),				// 3004 [dec]
		READER_CARD_COMM_ERROR(0xBBD),			// 3005 [dec]
		PC_READER_COMM_ERROR(0xBBE),			// 3006 [dec]
		COMMIT_TRANSACTION_NO_REPLY(0xBBF),	// 3007 [dec]
		COMMIT_TRANSACTION_ERROR(0xBC0),		// 3008 [dec]
		NOT_SUPPORTED_KEY_TYPE(0xBC2),			// 3010 [dec]

		DESFIRE_CARD_NO_CHANGES(0x0C0C),
		DESFIRE_CARD_OUT_OF_EEPROM_ERROR(0x0C0E),
		DESFIRE_CARD_ILLEGAL_COMMAND_CODE(0x0C1C),
		DESFIRE_CARD_INTEGRITY_ERROR(0x0C1E),
		DESFIRE_CARD_NO_SUCH_KEY(0x0C40),
		DESFIRE_CARD_LENGTH_ERROR(0x0C7E),
		DESFIRE_CARD_PERMISSION_DENIED(0x0C9D),
		DESFIRE_CARD_PARAMETER_ERROR(0x0C9E),
		DESFIRE_CARD_APPLICATION_NOT_FOUND(0x0CA0),
		DESFIRE_CARD_APPL_INTEGRITY_ERROR(0x0CA1),
		DESFIRE_CARD_AUTHENTICATION_ERROR(0x0CAE),
		DESFIRE_CARD_ADDITIONAL_FRAME(0x0CAF),
		DESFIRE_CARD_BOUNDARY_ERROR(0x0CBE),
		DESFIRE_CARD_PICC_INTEGRITY_ERROR(0x0CC1),
		DESFIRE_CARD_COMMAND_ABORTED(0x0CCA),
		DESFIRE_CARD_PICC_DISABLED_ERROR(0x0CCD),
		DESFIRE_CARD_COUNT_ERROR(0x0CCE),
		DESFIRE_CARD_DUPLICATE_ERROR(0x0CDE),
		DESFIRE_CARD_EEPROM_ERROR_DES(0x0CEE),
		DESFIRE_CARD_FILE_NOT_FOUND(0x0CF0),
		DESFIRE_CARD_FILE_INTEGRITY_ERROR(0x0CF1),
		DESFIRE_CATD_AUTHENTICATION_DELAY(0X0CAD),

		// uFCoder library errors:
		UFR_NOT_IMPLEMENTED(0x1000),
		UFR_COMMAND_FAILED(0x1001),
		UFR_TIMEOUT_ERR(0x1002),
		UFR_FILE_SYSTEM_ERROR(0x1003),
		UFR_FILE_SYSTEM_PATH_NOT_EXISTS(0x1004),
		UFR_FILE_NOT_EXISTS(0x1005),

		//SAM module error codes:
		UFR_SAM_APDU_ERROR(0x2000),
		UFR_SAM_AUTH_ERROR(0x2001),
		UFR_SAM_CRYPTO_ERROR(0x2002),

		// JC cards APDU Error Codes:
		UFR_APDU_TRANSCEIVE_ERROR(0xAE),
		UFR_APDU_JC_APP_NOT_SELECTED(0x6000),
		UFR_APDU_JC_APP_BUFF_EMPTY(0x6001),
		UFR_APDU_WRONG_SELECT_RESPONSE(0x6002),
		UFR_APDU_WRONG_KEY_TYPE(0x6003),
		UFR_APDU_WRONG_KEY_SIZE(0x6004),
		UFR_APDU_WRONG_KEY_PARAMS(0x6005),
		UFR_APDU_WRONG_SIGNING_ALGORITHM(0x6006),
		UFR_APDU_PLAIN_TEXT_MAX_SIZE_EXCEEDED(0x6007),
		UFR_APDU_UNSUPPORTED_KEY_SIZE(0x6008),
		UFR_APDU_UNSUPPORTED_ALGORITHMS(0x6009),
		UFR_APDU_PKI_OBJECT_NOT_FOUND(0x600A),
		UFR_APDU_MAX_PIN_LENGTH_EXCEEDED(0x600B),
		UFR_DIGEST_LENGTH_DOES_NOT_MATCH(0x600C),

		// reserved: 0x6100),
		CRYPTO_SUBSYS_NOT_INITIALIZED(0x6101),
		CRYPTO_SUBSYS_SIGNATURE_VERIFICATION_ERROR(0x6102),
		CRYPTO_SUBSYS_MAX_HASH_INPUT_EXCEEDED(0x6103),
		CRYPTO_SUBSYS_INVALID_HASH_ALGORITHM(0x6104),
		CRYPTO_SUBSYS_INVALID_CIPHER_ALGORITHM(0x6105),
		CRYPTO_SUBSYS_INVALID_PADDING_ALGORITHM(0x6106),
		CRYPTO_SUBSYS_WRONG_SIGNATURE(0x6107),
		CRYPTO_SUBSYS_WRONG_HASH_OUTPUT_LENGTH(0x6108),
		CRYPTO_SUBSYS_UNKNOWN_ECC_CURVE(0x6109),
		CRYPTO_SUBSYS_HASHING_ERROR(0x610A),
		CRYPTO_SUBSYS_INVALID_SIGNATURE_PARAMS(0x610B),
		CRYPTO_SUBSYS_INVALID_RSA_PUB_KEY(0x610C),
		CRYPTO_SUBSYS_INVALID_ECC_PUB_KEY_PARAMS(0x610D),
		CRYPTO_SUBSYS_INVALID_ECC_PUB_KEY(0x610E),

		UFR_WRONG_PEM_CERT_FORMAT(0x61C0),

		// X.509 specific statuses:
		X509_CAN_NOT_OPEN_FILE(0x6200),
		X509_WRONG_DATA(0x6201),
		X509_WRONG_LENGTH(0x6202),
		X509_UNSUPPORTED_PUBLIC_KEY_TYPE(0x6203),
		X509_UNSUPPORTED_PUBLIC_KEY_SIZE(0x6204),
		X509_UNSUPPORTED_PUBLIC_KEY_EXPONENT(0x6205),
		X509_EXTENSION_NOT_FOUND(0x6206),
		X509_WRONG_SIGNATURE(0x6207),
		X509_UNKNOWN_PUBLIC_KEY_TYPE(0x6208),
		X509_WRONG_RSA_PUBLIC_KEY_FORMAT(0x6209),
		X509_WRONG_ECC_PUBLIC_KEY_FORMAT(0x620A),
		X509_SIGNATURE_NOT_MATCH_CA_PUBLIC_KEY(0x620B),
		X509_UNSUPPORTED_SIGNATURE_SCH(0x620C),
		X509_UNSUPPORTED_ECC_CURVE(0x620D),

		// PKCS#7 specific statuses:
		PKCS7_WRONG_DATA(0x6241),
		PKCS7_UNSUPPORTED_SIGNATURE_SCHEME(0x6242),
		PKCS7_SIG_SCH_NOT_MATCH_CERT_KEY_TYPE(0x6243),

		PKCS7_WRONG_SIGNATURE(0x6247),

		// MRTD specific statuses:
		MRTD_SECURE_CHANNEL_SESSION_FAILED(0x6280),
		MRTD_WRONG_SOD_DATA(0x6281),
		MRTD_WRONG_SOD_LENGTH(0x6282),
		MRTD_UNKNOWN_DIGEST_ALGORITHM(0x6283),
		MRTD_WARNING_DOES_NOT_CONTAINS_DS_CERT(0x6284),
		MRTD_DATA_GROUOP_INDEX_NOT_EXIST(0x6285),
		MRTD_EF_COM_WRONG_DATA(0x6286),
		MRTD_EF_DG_WRONG_DATA(0x6287),
		MRTD_EF_DG1_WRONG_LDS_VERSION_LENGTH(0x6288),
		MRTD_VERIFY_CSCA_NOT_EXIST(0x6289),
		MRTD_VERIFY_WRONG_DS_SIGNATURE(0x628A),
		MRTD_VERIFY_WRONG_CSCA_SIGNATURE(0x628B),
		MRTD_MRZ_CHECK_ERROR(0x628C),

		// ICAO Master List specific statuses:
		ICAO_ML_WRONG_FORMAT(0x6300),
		ICAO_ML_CAN_NOT_OPEN_FILE(0x6301),
		ICAO_ML_CAN_NOT_READ_FILE(0x6302),
		ICAO_ML_CERTIFICATE_NOT_FOUND(0x6303),
		ICAO_ML_WRONG_SIGNATURE(0x6307),

		// EMV specific statuses
		SYS_ERR_OUT_OF_MEMORY(0x7001),
		EMV_ERR_WRONG_INPUT_DATA(0x7002),
		EMV_ERR_MAX_TAG_LEN_BYTES_EXCEEDED(0x7004),
		EMV_ERR_TAG_NOT_FOUND(0x7005),
		EMV_ERR_TAG_WRONG_SIZE(0x7006),
		EMV_ERR_TAG_WRONG_TYPE(0x7007),
		EMV_ERR_IN_CARD_READER(0x7008),
		EMV_ERR_READING_RECORD(0x7009),
		EMV_ERR_PDOL_IS_EMPTY(0x7010),
		EMV_ERR_LIST_FORMAT_NOT_FOUND(0x7011),
		EMV_ERR_AFL_NOT_FOUND(0x7012),
		EMV_ERR_AID_NOT_FOUND(0x7013),

		// ISO7816-4 Errors (R-APDU) - 2 SW bytes returned by the card), prefixed with 0x000A:
		UFR_APDU_SW_TAG(0x000A0000),
		UFR_APDU_SW_OPERATION_IS_FAILED(0x000A6300),
		UFR_APDU_SW_WRONG_LENGTH(0x000A6700),
		UFR_APDU_SW_SECURITY_STATUS_NOT_SATISFIED(0x000A6982),
		UFR_APDU_SW_AUTHENTICATION_METHOD_BLOCKED(0x000A6983),
		UFR_APDU_SW_DATA_INVALID(0x000A6984),
		UFR_APDU_SW_CONDITIONS_NOT_SATISFIED(0x000A6985),
		UFR_APDU_SW_WRONG_DATA(0x000A6A80),
		UFR_APDU_SW_FILE_NOT_FOUND(0x000A6A82),
		UFR_APDU_SW_RECORD_NOT_FOUND(0x000A6A83),
		UFR_APDU_SW_DATA_NOT_FOUND(0x000A6A88),
		UFR_APDU_SW_ENTITY_ALREADY_EXISTS(0x000A6A89),
		UFR_APDU_SW_INS_NOT_SUPPORTED(0x000A6D00),
		UFR_APDU_SW_NO_PRECISE_DIAGNOSTIC(0x000A6F00),

		MAX_UFR_STATUS(0x7FFFFFFF);

        int value;

        private ERRORCODES(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

    }
	 
	 
    protected static String sPlatformType = "";

    /**
     * specifies the type of platform isLinux() isWindows() isMac()
     *
     * @return .so , .dll or .dylib name based on the platform as String type
     */
    static public String GetPlatformType() {

        int os_type = Platform.getOSType();

        String lib_name = "uFCoder";
        String prefix = "";
        String postfix = "";
        String extension = "";

        switch (os_type) {
            case Platform.MAC:
                prefix = "lib";
                extension = "dylib";
                break;

            case Platform.LINUX:
                prefix = "lib";
                extension = "so";
                break;

            case Platform.WINDOWS:
                prefix = "";
                extension = "dll";
                break;
        }

        if (Platform.isARM()) {
            postfix = "arm";
        } else if (Platform.isPPC()) {
            postfix = "ppc";
        } else if (Platform.isIntel()) {
            postfix = "x86";
        }

        if (Platform.is64Bit()) {
            postfix += "_64";
        }

        // patch for OSX
        if (os_type == Platform.MAC && Platform.isIntel()) {
            postfix = "";
        }

        sPlatformType = prefix + lib_name;

        if (postfix.length() > 0) {
            sPlatformType += "-" + postfix;
        }

        sPlatformType += "." + extension;

        System.out.println("lib: " + sPlatformType);

        return sPlatformType;
    }

    /**
     * Returns the full path to the required libraries
     *
     * @param isApplet
     * @return String
     */
    static public String GetLibFullPath(boolean isApplet) {

        String lib_path = System.getProperty("user.dir");
        
        switch(Platform.getOSType())
        {
            case Platform.UNSPECIFIED:
                // throw exception
                
                break;
                
            case Platform.WINDOWS:
                if (Platform.is64Bit()){
                   lib_path += "\\ufr-lib\\windows\\x86_64\\"; 
                }else{
                   lib_path += "\\ufr-lib\\windows\\x86\\"; 
                }                                
                break;

            case Platform.MAC:
                if (Platform.is64Bit()){
                   lib_path += "\\ufr-lib\\macos\\x86_64\\"; 
                }
                break;
            case Platform.LINUX:
                if (Platform.is64Bit()){
                    lib_path += "\\ufr-lib\\linux\\x86_64\\";
                }else
                {
                   lib_path += "\\ufr-lib\\linux\\x86\\"; 
                }
            default:
                
                lib_path += "/ufr-lib/";
                
                break;
        }

        if (isApplet) {
            lib_path = ""; // only for applet
        }
        lib_path += GetPlatformType();

        System.out.println("GetLibFullPath(): " + lib_path);
        

        return lib_path;
    }

    public interface uFrFunctions extends Library {
    	
        String UFR_Status2String(int status);
        
        String GetDllVersionStr();
        
        int GetDlogicCardType(ByteByReference card_type);
        
        int TagEmulationStart();
        
        int TagEmulationStop();
        
        int TagEmulationStartRam();
        
        int TagEmulationStopRam();
        
        int erase_last_ndef_record(byte message_nr);
        
        int erase_all_ndef_records(byte message_nr);
        
        int ndef_card_initialization();
        
        int write_ndef_record_mirroring(byte message_nr, ByteByReference tnf, byte[] type_record, ByteByReference type_length,
                                              byte[] id, ByteByReference id_length, byte[] payload, IntByReference payload_length,
                                              ByteByReference card_formated, int use_uid_ascii_mirror, int use_counter_ascii_mirror,
                                              int payload_mirroring_pos);
        
        int write_ndef_record(byte message_nr, ByteByReference tnf, byte[] type_record, ByteByReference type_length, byte[] id,
                                    ByteByReference id_length, byte[] payload, IntByReference payload_length, ByteByReference card_formated);
        
        int WriteEmulationNdef(byte tnf, byte[] type_record, byte type_length, byte[] id, byte id_length,
                                     byte[] payload, byte payload_length);
        
        int WriteEmulationNdefRam(byte tnf, byte[] type_record, byte type_length,
				  byte[] id, byte id_length, byte[] payload, int payload_length);
        
        int read_ndef_record(byte message_nr, byte record_nr, ByteByReference tnf, byte[] type_record,
                                   ByteByReference type_length, byte[] id, ByteByReference id_length, byte[] payload,
                                   IntByReference payload_length);
        
        int WriteNdefRecord_WiFi(byte ndef_storage, String ssid, byte auth_type, byte encryption_type, String password);
        
        int ReadNdefRecord_WiFi(byte[] ssid, byte[]  auth_type, byte[] encryption_type, byte[] password);
        
        int WriteNdefRecord_Bluetooth(byte ndef_storage, String bt_mac_address);
        
        int ReadNdefRecord_Bluetooth(byte[] bt_mac_address);
        
        int WriteNdefRecord_SMS(byte ndef_storage, String phone_number, String message);
        
        int ReadNdefRecord_SMS(byte[] phone_number, byte[] message);
        
        int WriteNdefRecord_GeoLocation(byte ndef_storage, String latitude, String longitude);
        
        int ReadNdefRecord_GeoLocation(byte[] latitude, byte[] longitude);
        
        int WriteNdefRecord_NaviDestination(byte ndef_storage, String destination);
        
        int ReadNdefRecord_NaviDestination(byte[] destination);
        
        int WriteNdefRecord_Email(byte ndef_storage, String email_address, String subject, String message);
        
        int WriteNdefRecord_Address(byte ndef_storage, String address);
        
        int ReadNdefRecord_Address(byte[] address);
        
        int ReadNdefRecord_Email(byte[] email_address, byte[] subject, byte[]message);
        
        int WriteNdefRecord_AndroidApp(byte ndef_storage, String package_name);
        
        int ReadNdefRecord_AndroidApp(byte[] package_name);
        
        int WriteNdefRecord_Text(byte ndef_storage, String text);
        
        int ReadNdefRecord_Text(byte[] text);
        
        int WriteNdefRecord_StreetView(byte ndef_storage, String latitude, String longitude);
        
        int ReadNdefRecord_StreetView(byte[] latitude, byte[] longitude);
        
        int WriteNdefRecord_Phone(byte ndef_storage, String phone_number);
        
        int ReadNdefRecord_Phone(byte[] phone_number);
        
        int WriteNdefRecord_Contact(byte ndef_storage, String name, String company, String address,
                                    String phone, String email, String website);
        
        int ReadNdefRecord_Contact(byte[] vCard);
        
        int WriteNdefRecord_Bitcoin(byte ndef_storage, String bitcoin_address, String amount, String message);
        
        int ReadNdefRecord_Bitcoin(byte[] bitcoin_address, byte[] amount, byte[] message);
        
        int WriteNdefRecord_Skype(byte ndef_storage, String user_name, byte action);
        
        int ReadNdefRecord_Skype(byte[] user_name, byte[] action);
        
        int WriteNdefRecord_Whatsapp(byte ndef_storage, String message);
        
        int WriteNdefRecord_Viber(byte ndef_storage, String message);
        
        int ReadNdefRecord_Whatsapp(byte[] message);
        
        int ReadNdefRecord_Viber(byte[] message);
        
        int ReaderOpenEx(int reader_type, byte[] port_name, int port_interface, byte[] arg);
        
        int GetReaderType(IntByReference reader_type);

        int GetReaderSerialNumber(IntByReference reader_serial);

        int LinearFormatCard(byte[] key_a, byte block_access_bits, byte sector_access_bits, byte sector_trailers_byte9,
                byte[] key_b, ShortByReference sector_formatted, byte auth_mode, byte key_index);

        int ReaderOpen();
        
        int ReaderReset();

        int ReaderClose();

        int GetReaderType(int[] iaReaderType);

        int GetCardId(ByteByReference bCardType, IntByReference iCardSerial);

        int GetCardIdEx(ByteByReference bCardType, byte[] baCardUID, ByteByReference bUidSize);

        int ReaderUISignal(int iLightMode, int iSoundMode);

        int LinearRead(byte[] baReadData, int iLinearAddress, int iDataLength, ShortByReference shBytesRet, byte bAuthMode, byte bKeyIndex);

        int LinearWrite(byte[] baWriteData, int iLinearAddress, int iDataLength, ShortByReference shBytesWritten, byte bAuthMode, byte bKeyIndex);

        int BlockWrite(byte[] saBlockWrite, int iBlockAddress, byte bAuthMode, byte bKeyIndex);

        int GetReaderSerialNumber(int[] reader_serial);

        int GetCardId(byte[] card_id, int[] card_serial);

        int ReaderSoftRestart();        

        int ReadUserData(byte[] read_user_data);

        int WriteUserData(byte[] write_user_data);

        int ReaderKeyWrite(byte[] reader_key, byte key_index);

        int LinearRead(byte[] aucdata, int linear_address, int data_length, ShortByReference bytes_ret, byte auth_mode, int key_index);

        int LinearRead_AKM1(byte[] aucdata, int linear_address, int data_length, ShortByReference bytes_ret, byte auth_mode);

        int LinearRead_AKM2(byte[] aucdata, int linear_address, int data_length, ShortByReference bytes_ret, byte auth_mode);

        int LinearRead_PK(byte[] aucdata, int linear_address, int data_length, ShortByReference bytes_ret, byte auth_mode, byte[] pk_key);

        int LinearWrite(byte[] aucdata, int linear_address, int data_length, ShortByReference bytes_written, byte auth_mode, int key_index);

        int LinearWrite_AKM1(byte[] aucdata, int linear_address, int data_length, ShortByReference bytes_written, byte auth_mode);

        int LinearWrite_AKM2(byte[] aucdata, int linear_address, int data_length, ShortByReference bytes_written, byte auth_mode);

        int LinearWrite_PK(byte[] aucdata, int linear_address, int data_length, ShortByReference bytes_written, byte auth_mode, byte[] pk_key);

        int BlockRead(byte[] block_data, byte block_address, byte auth_mode, byte key_index);

        int BlockRead_AKM1(byte[] block_data, byte block_address, byte auth_mode);

        int BlockRead_AKM2(byte[] block_data, byte block_address, byte auth_mode);

        int BlockRead_PK(byte[] block_data, byte block_address, byte auth_mode, byte[] pk_key);

        int BlockWrite(byte[] block_data, byte block_address, byte auth_mode, byte key_index);

        int BlockWrite_AKM1(byte[] block_data, byte block_address, byte auth_mode);

        int BlockWrite_AKM2(byte[] block_data, byte block_address, byte auth_mode);

        int BlockWrite_PK(byte[] block_data, byte block_address, byte auth_mode, byte[] pk_key);

        int BlockInSectorRead(byte[] block_data, byte sector_address, byte block_address, byte auth_mode, byte key_index);

        int BlockInSectorRead_AKM1(byte[] block_data, byte sector_address, byte block_address, byte auth_mode);

        int BlockInSectorRead_AKM2(byte[] block_data, byte sector_address, byte block_address, byte auth_mode);

        int BlockInSectorRead_PK(byte[] block_data, byte sector_address, byte block_address, byte auth_mode, byte[] pk_key);

        int BlockInSectorWrite(byte[] block_data, byte sector_address, byte block_address, byte auth_mode, byte key_index);

        int BlockInSectorWrite_AKM1(byte[] block_data, byte sector_address, byte block_address, byte auth_mode);

        int BlockInSectorWrite_AKM2(byte[] block_data, byte sector_address, byte block_address, byte auth_mode);

        int BlockInSectorWrite_PK(byte[] block_data, byte sector_address, byte block_address, byte auth_mode, byte[] pk_key);

        int ValueBlockRead(IntByReference value_read, ShortByReference value_address, byte block_address, byte auth_mode, byte key_index);

        int ValueBlockRead_AKM1(IntByReference value_read, ShortByReference value_address, byte block_address, byte auth_mode);

        int ValueBlockRead_AKM2(IntByReference value_read, ShortByReference value_address, byte block_address, byte auth_mode);

        int ValueBlockRead_PK(IntByReference value_read, ShortByReference value_address, byte block_address, byte auth_mode, byte[] pk_key);

        int ValueBlockWrite(int value_read, short value_address, byte block_address, byte auth_mode, byte key_index);

        int ValueBlockWrite_AKM1(int value_read, short value_address, byte block_address, byte auth_mode);

        int ValueBlockWrite_AKM2(int value_read, short value_address, byte block_address, byte auth_mode);

        int ValueBlockWrite_PK(int value_read, short value_address, byte block_address, byte auth_mode, byte[] pk_key);

        int ValueBlockIncrement(int incr_value, byte block_address, byte auth_mode, byte key_index);

        int ValueBlockIncrement_AKM1(int incr_value, byte block_address, byte auth_mode);

        int ValueBlockIncrement_AKM2(int incr_value, byte block_address, byte auth_mode);

        int ValueBlockIncrement_PK(int incr_value, byte block_address, byte auth_mode, byte[] pk_key);

        int ValueBlockDecrement(int incr_value, byte block_address, byte auth_mode, byte key_index);

        int ValueBlockDecrement_AKM1(int incr_value, byte block_address, byte auth_mode);

        int ValueBlockDecrement_AKM2(int incr_value, byte block_address, byte auth_mode);

        int ValueBlockDecrement_PK(int incr_value, byte block_address, byte auth_mode, byte[] pk_key);

        int ValueBlockInSectorRead(IntByReference read_data, ShortByReference value_address, byte sector_address, byte block_address, byte auth_mode, byte key_index);

        int ValueBlockInSectorRead_AKM1(IntByReference read_data, ShortByReference value_address, byte sector_address, byte block_address, byte auth_mode);

        int ValueBlockInSectorRead_AKM2(IntByReference read_data, ShortByReference value_address, byte sector_address, byte block_address, byte auth_mode);

        int ValueBlockInSectorRead_PK(IntByReference read_data, ShortByReference value_address, byte sector_address, byte block_address, byte auth_mode, byte[] pk_key);

        int ValueBlockInSectorWrite(int read_data, short value_address, byte sector_address, byte block_address, byte auth_mode, byte key_index);

        int ValueBlockInSectorWrite_AKM1(int read_data, short value_address, byte sector_address, byte block_address, byte auth_mode);

        int ValueBlockInSectorWrite_AKM2(int read_data, short value_address, byte sector_address, byte block_address, byte auth_mode);

        int ValueBlockInSectorWrite_PK(int read_data, short value_address, byte sector_address, byte block_address, byte auth_mode, byte[] pk_key);

        int ValueBlockInSectorIncrement(int read_data, byte sector_address, byte block_address, byte auth_mode, byte key_index);

        int ValueBlockInSectorIncrement_AKM1(int read_data, byte sector_address, byte block_address, byte auth_mode);

        int ValueBlockInSectorIncrement_AKM2(int read_data, byte sector_address, byte block_address, byte auth_mode);

        int ValueBlockInSectorIncrement_PK(int read_data, byte sector_address, byte block_address, byte auth_mode, byte[] pk_key);

        int ValueBlockInSectorDecrement(int read_data, byte sector_address, byte block_address, byte auth_mode, byte key_index);

        int ValueBlockInSectorDecrement_AKM1(int read_data, byte sector_address, byte block_address, byte auth_mode);

        int ValueBlockInSectorDecrement_AKM2(int read_data, byte sector_address, byte block_address, byte auth_mode);

        int ValueBlockInSectorDecrement_PK(int read_data, byte sector_address, byte block_address, byte auth_mode, byte[] pk_key);

        int LinearFormatCard(byte[] key_a, byte block_access_bits, byte sector_access_bits, byte sector_trailers_byte9,
                byte[] key_b, ByteByReference sector_formatted, byte auth_mode, byte key_index);

        int LinearFormatCard_AKM1(byte[] key_a, byte block_access_bits, byte sector_access_bits, byte sector_trailers_byte9,
                byte[] key_b, ByteByReference sector_formatted, byte auth_mode);

        int LinearFormatCard_AKM2(byte[] key_a, byte block_access_bits, byte sector_access_bits, byte sector_trailers_byte9,
                byte[] key_b, ByteByReference sector_formatted, byte auth_mode);

        int LinearFormatCard_PK(byte[] key_a, byte block_access_bits, byte sector_access_bits, byte sector_trailers_byte9,
                byte[] key_b, ByteByReference sector_formatted, byte auth_mode, byte[] pk_key);

        int SectorTrailerWrite(byte addressing_mode, byte address, byte[] key_a, byte block_access_bits0,
                byte block_access_bits1, byte block_access_bits2, byte sector_trailer_access_bits,
                byte sector_trailer_byte9, byte[] key_b, byte auth_mode, byte key_index);

        int SectorTrailerWrite_AKM1(byte addressing_mode, byte address, byte[] key_a, byte block_access_bits0,
                byte block_access_bits1, byte block_access_bits2, byte sector_trailer_access_bits,
                byte sector_trailer_byte9, byte[] key_b, byte auth_mode);

        int SectorTrailerWrite_AKM2(byte addressing_mode, byte address, byte[] key_a, byte block_access_bits0,
                byte block_access_bits1, byte block_access_bits2, byte sector_trailer_access_bits,
                byte sector_trailer_byte9, byte[] key_b, byte auth_mode);

        int SectorTrailerWrite_PK(byte addressing_mode, byte address, byte[] key_a, byte block_access_bits0,
                byte block_access_bits1, byte block_access_bits2, byte sector_trailer_access_bits,
                byte sector_trailer_byte9, byte[] key_b, byte auth_mode, byte[] pk_key);
        
        int SetISO14443_4_Mode();
        
        int APDUTransceive(byte cls, byte ins, byte p1, byte p2, byte[] data_out, int Nc, byte[] data_in, IntByReference Ne,
                byte send_le, byte[] apdu_status);
        
        int s_block_deselect(byte timeout);
        
        int EMV_GetPAN(String df_name, byte[] pan);
        
        int EMV_GetLastTransaction(String df_name, byte[] last_transaction_info);

        
    }
}