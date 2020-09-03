package apdu_credit_card;

import static apdu_credit_card.UfrCoder.ERRORCODES;
import static apdu_credit_card.UfrCoder.GetLibFullPath;
import static apdu_credit_card.UfrCoder.uFrFunctions;

import javax.swing.JOptionPane;

import com.sun.jna.Native;
import com.sun.jna.ptr.IntByReference;

import apdu_credit_card.UfrCoder.uFrFunctions;


public class UFrEMV
{
	public uFrFunctions ufr;
	
	public UFrEMV()
	{
		try {
            ufr = (uFrFunctions) Native.loadLibrary(GetLibFullPath(false), uFrFunctions.class);
        } catch (UnsatisfiedLinkError e) {
            JOptionPane.showMessageDialog(null, "Unable to load library for path : " + GetLibFullPath(false));
        }
	}
	
	public enum tag_type_t {
     	STR(10),
     	LANGUAGE_CODE_PAIRS(11),
     	BCD_4BY4(12),
     	DEC_UINT8(13),
     	DEC_UINT16(14),
     	DEC_UINT32(15),
     	ISO3166_COUNTRY(16),
     	ISO4217_CURRENCY(17),
     	DATE_YMD(18),
     	BIN_OR_STR(19),
     	BIN(20),
     	TL_LIST(21),
     	NODE(22);
     	
     	 private int value;

         private tag_type_t(int value) {
             this.value = value;
         }

         public int getValue() {
             return value;
         }
     			//-------------------
     }
	 
	//------------------------------------------------------------
	 
	 public class emv_tags_t extends emv_tags_s {

		public emv_tags_t(int tag_init, String init_desc, tag_type_t init_tag_type, byte init_tag_id_len) {
			super(tag_init, init_desc, init_tag_type, init_tag_id_len);
			// TODO Auto-generated constructor stub
		} 
	};
	 
	public class emv_tags_s {
		int tag;
		public String description;
		public tag_type_t tag_type;
		public byte tag_id_len;
		 
		public emv_tags_s(int tag_init, String init_desc, tag_type_t init_tag_type, byte init_tag_id_len)
	    {
	           tag = tag_init;
	           description = init_desc;
	           tag_type = init_tag_type;
	           tag_id_len = init_tag_id_len;
	    }
		 
	 }	
	 
	
	    
	 public class afl_list_item_t extends afl_list_item_s { };

	    public class afl_list_item_s
	    {
	        public byte[] sfi = new byte[1];

	        public byte[] record_first = new byte[1];

	        public byte[] record_last = new byte[1];

	        public byte[] record_num_offline_auth = new byte[1];

	        public afl_list_item_t next;

	    }
	    
	

	public  class iso4217_currency_code_s
	{
	    public short num_code;
        public String alpha_code;
        public String currency;

        public iso4217_currency_code_s(short num_code_init, String alpha_code_init, String currency_init)
	    {
	        num_code = num_code_init;
	        alpha_code = alpha_code_init;
            currency = currency_init;
        }
      }
	
	public static class emv_tree_node_t extends emv_tree_node_s {};
	
	public static class emv_tree_node_s
    {
        public int[] tag = new int[1];

        public byte[] tag_bytes = new byte[1];

        public String description = "";

        public tag_type_t tag_type;

        public boolean[] is_node_type = new boolean[1];

        public byte[] value = new byte[1024];
        
        public int[] value_len = new int[1];
        
        public emv_tree_node_t tl_list_format;
        
        public emv_tree_node_t next;
        
        public emv_tree_node_t subnode;
    }
	  //------------------------------------------------------------
	
    public  emv_tags_s[] emv_tags = {
    new emv_tags_s(0x9f01, "Acquirer Identifier", tag_type_t.BIN, (byte)2),
    new emv_tags_s(0x9f02, "Amount, Authorised (Numeric)", tag_type_t.BIN, (byte)2),
    new emv_tags_s(0x9f03, "Amount, Other (Numeric)", tag_type_t.BIN, (byte)2),
    new emv_tags_s(0x9f04, "Amount, Other (Binary)", tag_type_t.BIN, (byte)2),
    new emv_tags_s(0x9f05, "Application Discretionary Data", tag_type_t.BIN, (byte)2),
    new emv_tags_s(0x9f06, "Application Identifier (AID) - terminal", tag_type_t.BIN, (byte)2),
    new emv_tags_s(0x9f07, "Application Usage Control", tag_type_t.BIN, (byte)2),
    new emv_tags_s(0x9f08, "Application Version Number", tag_type_t.BIN, (byte)2),
    new emv_tags_s(0x9f09, "Application Version Number", tag_type_t.BIN, (byte)2),
    new emv_tags_s(0x9f0b, "Cardholder Name Extended", tag_type_t.BIN, (byte)2),
    new emv_tags_s(0xbf0c, "FCI Issuer Discretionary Data", tag_type_t.NODE, (byte)2),
    new emv_tags_s(0x9f0d, "Issuer Action Code - Default", tag_type_t.BIN, (byte)2),
    new emv_tags_s(0x9f0e, "Issuer Action Code - Denial", tag_type_t.BIN, (byte)2),
    new emv_tags_s(0x9f0f, "Issuer Action Code - Online", tag_type_t.BIN, (byte)2),
    new emv_tags_s(0x9f10, "Issuer Application Data", tag_type_t.BIN, (byte)2),
    new emv_tags_s(0x9f11, "Issuer Code Table Index", tag_type_t.BIN, (byte)2),
    new emv_tags_s(0x9f12, "Application Preferred Name", tag_type_t.BIN, (byte)2),
    new emv_tags_s(0x9f13, "Last Online Application Transaction Counter (ATC) Register", tag_type_t.BIN, (byte)2),
    new emv_tags_s(0x9f14, "Lower Consecutive Offline Limit", tag_type_t.BIN, (byte)2),
    new emv_tags_s(0x9f15, "Merchant Category Code", tag_type_t.BIN, (byte)2),
    new emv_tags_s(0x9f16, "Merchant Identifier", tag_type_t.BIN, (byte)2),
    new emv_tags_s(0x9f17, "Personal Identification Number (PIN) Try Counter", tag_type_t.BIN, (byte)2),
    new emv_tags_s(0x9f18, "Issuer Script Identifier", tag_type_t.BIN, (byte)2),
    new emv_tags_s(0x9f1a, "Terminal Country Code", tag_type_t.ISO3166_COUNTRY, (byte)2),
    new emv_tags_s(0x9f1b, "Terminal Floor Limit", tag_type_t.BIN, (byte)2),
    new emv_tags_s(0x9f1c, "Terminal Identification", tag_type_t.BIN, (byte)2),
    new emv_tags_s(0x9f1d, "Terminal Risk Management Data", tag_type_t.BIN, (byte)2),
    new emv_tags_s(0x9f1e, "Interface Device (IFD) Serial Number", tag_type_t.BIN, (byte)2),
    new emv_tags_s(0x9f1f, "Track 1 Discretionary Data", tag_type_t.BIN, (byte)2),
    new emv_tags_s(0x5f20, "Cardholder Name", tag_type_t.STR, (byte)2),
    new emv_tags_s(0x9f21, "Transaction Time", tag_type_t.BIN, (byte)2),
    new emv_tags_s(0x9f22, "Certification Authority Public Key Index", tag_type_t.BIN, (byte)2),
    new emv_tags_s(0x9f23, "Upper Consecutive Offline Limit", tag_type_t.BIN, (byte)2),
    new emv_tags_s(0x5f24, "Application Expiration Date", tag_type_t.DATE_YMD, (byte)2),
    new emv_tags_s(0x5f25, "Application Effective Date", tag_type_t.DATE_YMD, (byte)2),
    new emv_tags_s(0x9f26, "Application Cryptogram", tag_type_t.BIN, (byte)2),
    new emv_tags_s(0x9f27, "Cryptogram Information Data", tag_type_t.BIN, (byte)2),
    new emv_tags_s(0x5f28, "Issuer Country Code", tag_type_t.ISO3166_COUNTRY, (byte)2),
    new emv_tags_s(0x5f2a, "Transaction Currency Code", tag_type_t.ISO4217_CURRENCY, (byte)2),
    new emv_tags_s(0x5f2d, "Language Preference", tag_type_t.LANGUAGE_CODE_PAIRS, (byte)2),
    new emv_tags_s(0x9f2e, "Integrated Circuit Card (ICC) PIN Encipherment Public Key Exponent", tag_type_t.BIN, (byte)2),
    new emv_tags_s(0x9f2f, "Integrated Circuit Card (ICC) PIN Encipherment Public Key Remainder", tag_type_t.BIN, (byte)2),
    new emv_tags_s(0x5f30, "Service Code", tag_type_t.BIN, (byte)2),
    new emv_tags_s(0x9f32, "Issuer Public Key Exponent", tag_type_t.BIN, (byte)2),
    new emv_tags_s(0x9f33, "Terminal Capabilities", tag_type_t.BIN, (byte)2),
    new emv_tags_s(0x5f34, "Application PAN Sequence Number (PSN)", tag_type_t.BIN, (byte)2),
    new emv_tags_s(0x9f35, "Terminal Type", tag_type_t.BIN, (byte)2),
    new emv_tags_s(0x5f36, "Transaction Currency Exponent", tag_type_t.BIN, (byte)2),
    new emv_tags_s(0x9f37, "Unpredictable Number", tag_type_t.BIN, (byte)2),
    new emv_tags_s(0x9f38, "Processing Options Data Object List (PDOL)", tag_type_t.TL_LIST, (byte)2),
    new emv_tags_s(0x9f34, "Cardholder Verification Method (CVM) Results", tag_type_t.BIN, (byte)2),
    new emv_tags_s(0x9f3a, "Amount, Reference Currency", tag_type_t.BIN, (byte)2),
    new emv_tags_s(0x9f3b, "Application Reference Currency", tag_type_t.BIN, (byte)2),
    new emv_tags_s(0x9f3c, "Transaction Reference Currency Code", tag_type_t.BIN, (byte)2),
    new emv_tags_s(0x9f3d, "Transaction Reference Currency Exponent", tag_type_t.BIN, (byte)2),
    new emv_tags_s(0x9f40, "Additional Terminal Capabilities", tag_type_t.BIN, (byte)2),
    new emv_tags_s(0x9f41, "Transaction Sequence Counter", tag_type_t.BIN, (byte)2),
    new emv_tags_s(0x9f43, "Application Reference Currency Exponent", tag_type_t.BIN, (byte)2),
    new emv_tags_s(0x9f44, "Application Currency Exponent", tag_type_t.BIN, (byte)2),
    new emv_tags_s(0x9f2d, "Integrated Circuit Card (ICC) PIN Encipherment Public Key Certificate", tag_type_t.BIN, (byte)2),
    new emv_tags_s(0x9f46, "Integrated Circuit Card (ICC) Public Key Certificate", tag_type_t.BIN, (byte)2),
    new emv_tags_s(0x9f47, "Integrated Circuit Card (ICC) Public Key Exponent", tag_type_t.BIN, (byte)2),
    new emv_tags_s(0x9f48, "Integrated Circuit Card (ICC) Public Key Remainder", tag_type_t.BIN, (byte)2),
    new emv_tags_s(0x9f49, "Dynamic Data Authentication Data Object List (DDOL)", tag_type_t.BIN, (byte)2),
    new emv_tags_s(0x9f4a, "Static Data Authentication Tag List", tag_type_t.BIN, (byte)2),
    new emv_tags_s(0x9f4b, "Signed Dynamic Application Data", tag_type_t.BIN, (byte)2),
    new emv_tags_s(0x9f4c, "ICC Dynamic Number", tag_type_t.BIN, (byte)2),
    new emv_tags_s(0x9f4d, "Log Entry", tag_type_t.BIN, (byte)2),
    new emv_tags_s(0x9f4e, "Merchant Name and Location", tag_type_t.BIN, (byte)2),
    new emv_tags_s(0x9f51, "Application Currency Code", tag_type_t.BIN, (byte)2),
    new emv_tags_s(0x9f52, "Card Verification Results (CVR)", tag_type_t.BIN, (byte)2),
    new emv_tags_s(0x5f53, "International Bank Account Number (IBAN)", tag_type_t.BIN, (byte)2),
    new emv_tags_s(0x5f54, "Bank Identifier Code (BIC)", tag_type_t.BIN, (byte)2),
    new emv_tags_s(0x5f55, "Issuer Country Code (alpha2 format)", tag_type_t.BIN, (byte)2),
    new emv_tags_s(0x5f56, "Issuer Country Code (alpha3 format)", tag_type_t.BIN, (byte)2),
    new emv_tags_s(0x9f58, "Lower Consecutive Offline Limit (Card Check)", tag_type_t.BIN, (byte)2),
    new emv_tags_s(0x9f59, "Upper Consecutive Offline Limit (Card Check)", tag_type_t.BIN, (byte)2),
    new emv_tags_s(0x9f5c, "Cumulative Total Transaction Amount Upper Limit", tag_type_t.BIN, (byte)2),
    new emv_tags_s(0x9f72, "Consecutive Transaction Limit (International - Country)", tag_type_t.BIN, (byte)2),
    new emv_tags_s(0x9f7c, "Merchant Custom Data", tag_type_t.BIN, (byte)2),
    new emv_tags_s(0x9F62, "PCVC3 (Track1)", tag_type_t.BIN, (byte)2),
    new emv_tags_s(0x9F63, "PUNATC (Track1)", tag_type_t.BIN, (byte)2),
    new emv_tags_s(0x9F64, "NATC (Track1)", tag_type_t.BIN, (byte)2),
    new emv_tags_s(0x9f65, "Track 2 Bit Map for CVC3", tag_type_t.BIN, (byte)2),
    new emv_tags_s(0x9f66, "Terminal Transaction Qualifiers (TTQ)", tag_type_t.BIN, (byte)2),
    new emv_tags_s(0x9f67, "NATC (Track2)", tag_type_t.BIN, (byte)2),
    new emv_tags_s(0x9f68, "Mag Stripe CVM List", tag_type_t.BIN, (byte)2),
    new emv_tags_s(0x9f69, "Unpredictable Number Data Object List (UDOL)", tag_type_t.BIN, (byte)2),
    new emv_tags_s(0x9f6b, "Track 2 Data", tag_type_t.BIN, (byte)2),
    new emv_tags_s(0x9f6c, "Mag Stripe Application Version Number (Card)", tag_type_t.BIN, (byte)2),
    new emv_tags_s(0x9f6e, "Third Party Data", tag_type_t.BIN, (byte)2),
    new emv_tags_s(0x9f74, "VLP Issuer Authorization Code", tag_type_t.BIN, (byte)2),
    new emv_tags_s(0x9f75, "Cumulative Total Transaction Amount Limit - Dual Currency", tag_type_t.BIN, (byte)2),
    new emv_tags_s(0x9f76, "Secondary Application Currency Code", tag_type_t.ISO4217_CURRENCY, (byte)2),
    new emv_tags_s(0x9f7d, "Unknown Tag", tag_type_t.BIN, (byte)2),
    new emv_tags_s(0x9f7f, "Card Production Life Cycle (CPLC) History File Identifiers", tag_type_t.BIN, (byte)2),
    new emv_tags_s(0x9f45, "Data Authentication Code", tag_type_t.BIN, (byte)2),
    new emv_tags_s(0x9f57, "Issuer Country Code", tag_type_t.BIN, (byte)2),
    new emv_tags_s(0x9f39, "Point-of-Service (POS) Entry Mode", tag_type_t.BIN, (byte)2),
    new emv_tags_s(0x9f73, "Currency Conversion Factor", tag_type_t.BIN, (byte)2),
    new emv_tags_s(0x9f42, "Application Currency Code", tag_type_t.ISO4217_CURRENCY, (byte)2),
    new emv_tags_s(0x9f56, "Issuer Authentication Indicator", tag_type_t.BIN, (byte)2),
    new emv_tags_s(0x9f20, "Track 2 Discretionary Data", tag_type_t.BIN, (byte)2),
    new emv_tags_s(0xdf01, "Reference PIN", tag_type_t.BIN, (byte)2),
    new emv_tags_s(0x9f36, "Application Transaction Counter (ATC)", tag_type_t.BIN, (byte)2),
    new emv_tags_s(0x9f4f, "Log Format", tag_type_t.TL_LIST, (byte)2),
    new emv_tags_s(0x5f50, "Issuer URL", tag_type_t.BIN, (byte)2),
    new emv_tags_s(0x9f5a, "Issuer URL2", tag_type_t.BIN, (byte)2),
    new emv_tags_s(0x9f53, "Consecutive Transaction Limit (International)", tag_type_t.BIN, (byte)2),
    new emv_tags_s(0x9f54, "Cumulative Total Transaction Amount Limit", tag_type_t.BIN, (byte)2),
    new emv_tags_s(0x9f55, "Geographic Indicator", tag_type_t.BIN, (byte)2),
    new emv_tags_s(0x42, "Issuer Identification Number (IIN)", tag_type_t.BIN, (byte)1),
    new emv_tags_s(0x4f, "Application Identifier (AID)", tag_type_t.BIN, (byte)1),
    new emv_tags_s(0x50, "Application Label", tag_type_t.STR, (byte)1),
    new emv_tags_s(0x56, "Track 1 Equivalent Data", tag_type_t.BIN, (byte)1),
    new emv_tags_s(0x57, "Track 2 Equivalent Data", tag_type_t.BIN, (byte)1),
    new emv_tags_s(0x5a, "Application Primary Account Number (PAN)", tag_type_t.BCD_4BY4, (byte)1),
    new emv_tags_s(0x61, "Application Template", tag_type_t.NODE, (byte)1),
    new emv_tags_s(0x6f, "File Control Information (FCI) Template", tag_type_t.NODE, (byte)1),
    new emv_tags_s(0x70, "Response Message Template / AEF Data Template", tag_type_t.NODE, (byte)1),
    new emv_tags_s(0x71, "Issuer Script Template 1", tag_type_t.BIN, (byte)1),
    new emv_tags_s(0x72, "Issuer Script Template 2", tag_type_t.BIN, (byte)1),
    new emv_tags_s(0x73, "Directory Discretionary Template", tag_type_t.BIN, (byte)1),
    new emv_tags_s(0x77, "Response Message Template Format 2", tag_type_t.NODE, (byte)1),
    new emv_tags_s(0x80, "Response Message Template Format 1", tag_type_t.BIN, (byte)1),
    new emv_tags_s(0x81, "Amount, Authorised (Binary)", tag_type_t.BIN, (byte)1),
    new emv_tags_s(0x82, "Application Interchange Profile (AIP)", tag_type_t.BIN, (byte)1),
    new emv_tags_s(0x83, "Command Template", tag_type_t.BIN, (byte)1),
    new emv_tags_s(0x84, "Dedicated File (DF) Name", tag_type_t.BIN_OR_STR, (byte)1),
    new emv_tags_s(0x86, "Issuer Script Command", tag_type_t.BIN, (byte)1),
    new emv_tags_s(0x87, "Application Priority Indicator", tag_type_t.BIN, (byte)1),
    new emv_tags_s(0x88, "Short File Identifier (SFI)", tag_type_t.BIN, (byte)1),
    new emv_tags_s(0x89, "Authorisation Code", tag_type_t.BIN, (byte)1),
    new emv_tags_s(0x8a, "Authorisation Response Code", tag_type_t.BIN, (byte)1),
    new emv_tags_s(0x8c, "Card Risk Management Data Object List 1 (CDOL1)", tag_type_t.BIN, (byte)1),
    new emv_tags_s(0x8d, "Card Risk Management Data Object List 2 (CDOL2)", tag_type_t.BIN, (byte)1),
    new emv_tags_s(0x8e, "Cardholder Verification Method (CVM) List", tag_type_t.BIN, (byte)1),
    new emv_tags_s(0x8f, "Certification Authority Public Key Index", tag_type_t.BIN, (byte)1),
    new emv_tags_s(0x90, "Issuer Public Key Certificate", tag_type_t.BIN, (byte)1),
    new emv_tags_s(0x91, "Issuer Authentication Data", tag_type_t.BIN, (byte)1),
    new emv_tags_s(0x92, "Issuer Public Key Remainder", tag_type_t.BIN, (byte)1),
    new emv_tags_s(0x93, "Signed Static Application Data", tag_type_t.BIN, (byte)1),
    new emv_tags_s(0x94, "Application File Locator (AFL)", tag_type_t.BIN, (byte)1),
    new emv_tags_s(0x95, "Terminal Verification Results", tag_type_t.BIN, (byte)1),
    new emv_tags_s(0x97, "Transaction Certificate Data Object List (TDOL)", tag_type_t.BIN, (byte)1),
    new emv_tags_s(0x98, "Transaction Certificate (TC) Hash Value", tag_type_t.BIN, (byte)1),
    new emv_tags_s(0x99, "Transaction Personal Identification Number (PIN) Data", tag_type_t.BIN, (byte)1),
    new emv_tags_s(0x9a, "Transaction Date", tag_type_t.DATE_YMD, (byte)1),
    new emv_tags_s(0x9b, "Transaction Status Information", tag_type_t.BIN, (byte)1),
    new emv_tags_s(0x9c, "Transaction Type", tag_type_t.BIN, (byte)1),
    new emv_tags_s(0x9d, "Directory Definition File (DDF) Name", tag_type_t.BIN, (byte)1),
    new emv_tags_s(0xa5, "File Control Information (FCI) Proprietary Template", tag_type_t.NODE, (byte)1),
    new emv_tags_s(0, "UNKNOWN TAG", tag_type_t.BIN, (byte)0)
    };


    public iso4217_currency_code_s[] iso4217_currency_codes =
    {
        new iso4217_currency_code_s( (short)8,   "ALL", "Albania Lek"),
        new iso4217_currency_code_s( (short)12,  "DZD", "Algeria Dinar" ),
        new iso4217_currency_code_s( (short)32,  "ARS", "Argentina Peso" ),
        new iso4217_currency_code_s( (short)36,  "AUD", "Australia Dollar" ),
        new iso4217_currency_code_s( (short)44,  "BSD", "Bahamas Dollar" ),
        new iso4217_currency_code_s( (short)48,  "BHD", "Bahrain Dinar" ),
        new iso4217_currency_code_s( (short)50,  "BDT", "Bangladesh Taka" ),
        new iso4217_currency_code_s( (short)51,  "AMD", "Armenia Dram" ),
        new iso4217_currency_code_s( (short)52,  "BBD", "Barbados Dollar" ),
        new iso4217_currency_code_s( (short)60,  "BMD", "Bermuda Dollar" ),
        new iso4217_currency_code_s( (short)64,  "BTN", "Bhutan Ngultrum" ),
        new iso4217_currency_code_s( (short)68,  "BOB", "Bolivia Bolíviano" ),
        new iso4217_currency_code_s( (short)72,  "BWP", "Botswana Pula" ),
        new iso4217_currency_code_s( (short)84,  "BZD", "Belize Dollar" ),
        new iso4217_currency_code_s( (short)90,  "SBD", "Solomon Islands Dollar" ),
        new iso4217_currency_code_s( (short)96,  "BND", "Brunei Darussalam Dollar" ),
        new iso4217_currency_code_s( (short)104, "MMK", "Myanmar (Burma) Kyat" ),
        new iso4217_currency_code_s( (short)108, "BIF", "Burundi Franc" ),
        new iso4217_currency_code_s( (short)116, "KHR", "Cambodia Riel" ),
        new iso4217_currency_code_s( (short)124, "CAD", "Canada Dollar" ),
        new iso4217_currency_code_s( (short)132, "CVE", "Cape Verde Escudo" ),
        new iso4217_currency_code_s( (short)136, "KYD", "Cayman Islands Dollar" ),
        new iso4217_currency_code_s( (short)144, "LKR", "Sri Lanka Rupee" ),
        new iso4217_currency_code_s( (short)152, "CLP", "Chile Peso" ),
        new iso4217_currency_code_s( (short)156, "CNY", "China Yuan Renminbi" ),
        new iso4217_currency_code_s( (short)170, "COP", "Colombia Peso" ),
        new iso4217_currency_code_s( (short)174, "KMF", "Comorian Franc" ),
        new iso4217_currency_code_s( (short)188, "CRC", "Costa Rica Colon" ),
        new iso4217_currency_code_s( (short)191, "HRK", "Croatia Kuna" ),
        new iso4217_currency_code_s( (short)192, "CUP", "Cuba Peso" ),
        new iso4217_currency_code_s( (short)203, "CZK", "Czech Republic Koruna" ),
        new iso4217_currency_code_s( (short)208, "DKK", "Denmark Krone" ),
        new iso4217_currency_code_s( (short)214, "DOP", "Dominican Republic Peso" ),
        new iso4217_currency_code_s( (short)222, "SVC", "El Salvador Colon" ),
        new iso4217_currency_code_s( (short)230, "ETB", "Ethiopia Birr" ),
        new iso4217_currency_code_s( (short)232, "ERN", "Eritrea Nakfa" ),
        new iso4217_currency_code_s( (short)238, "FKP", "Falkland Islands (Malvinas) Pound" ),
        new iso4217_currency_code_s( (short)242, "FJD", "Fiji Dollar" ),
        new iso4217_currency_code_s( (short)262, "DJF", "Djibouti Franc" ),
        new iso4217_currency_code_s( (short)270, "GMD", "Gambia Dalasi" ),
        new iso4217_currency_code_s( (short)292, "GIP", "Gibraltar Pound" ),
        new iso4217_currency_code_s( (short)320, "GTQ", "Guatemala Quetzal" ),
        new iso4217_currency_code_s( (short)324, "GNF", "Guinea Franc" ),
        new iso4217_currency_code_s( (short)328, "GYD", "Guyana Dollar" ),
        new iso4217_currency_code_s( (short)332, "HTG", "Haiti Gourde" ),
        new iso4217_currency_code_s( (short)340, "HNL", "Honduras Lempira" ),
        new iso4217_currency_code_s( (short)344, "HKD", "Hong Kong Dollar" ),
        new iso4217_currency_code_s( (short)348, "HUF", "Hungary Forint" ),
        new iso4217_currency_code_s( (short)352, "ISK", "Iceland Krona" ),
        new iso4217_currency_code_s( (short)356, "INR", "India Rupee" ),
        new iso4217_currency_code_s( (short)360, "IDR", "Indonesia Rupiah" ),
        new iso4217_currency_code_s( (short)364, "IRR", "Iran Rial" ),
        new iso4217_currency_code_s( (short)368, "IQD", "Iraq Dinar" ),
        new iso4217_currency_code_s( (short)376, "ILS", "Israel Shekel" ),
        new iso4217_currency_code_s( (short)388, "JMD", "Jamaica Dollar" ),
        new iso4217_currency_code_s( (short)392, "JPY", "Japan Yen" ),
        new iso4217_currency_code_s( (short)398, "KZT", "Kazakhstan Tenge" ),
        new iso4217_currency_code_s( (short)400, "JOD", "Jordan Dinar" ),
        new iso4217_currency_code_s( (short)404, "KES", "Kenya Shilling" ),
        new iso4217_currency_code_s( (short)408, "KPW", "Korea (North) Won" ),
        new iso4217_currency_code_s( (short)410, "KRW", "Korea (South) Won" ),
        new iso4217_currency_code_s( (short)414, "KWD", "Kuwait Dinar" ),
        new iso4217_currency_code_s( (short)417, "KGS", "Kyrgyzstan Som" ),
        new iso4217_currency_code_s( (short)418, "LAK", "Laos Kip" ),
        new iso4217_currency_code_s( (short)422, "LBP", "Lebanon Pound" ),
        new iso4217_currency_code_s( (short)426, "LSL", "Lesotho Loti" ),
        new iso4217_currency_code_s( (short)430, "LRD", "Liberia Dollar" ),
        new iso4217_currency_code_s( (short)434, "LYD", "Libya Dinar" ),
        new iso4217_currency_code_s( (short)446, "MOP", "Macau Pataca" ),
        new iso4217_currency_code_s( (short)454, "MWK", "Malawi Kwacha" ),
        new iso4217_currency_code_s( (short)458, "MYR", "Malaysia Ringgit" ),
        new iso4217_currency_code_s( (short)462, "MVR", "Maldives (Maldive Islands) Rufiyaa" ),
        new iso4217_currency_code_s( (short)478, "MRO", "Mauritania Ouguiya" ),
        new iso4217_currency_code_s( (short)480, "MUR", "Mauritius Rupee" ),
        new iso4217_currency_code_s( (short)484, "MXN", "Mexico Peso" ),
        new iso4217_currency_code_s( (short)496, "MNT", "Mongolia Tughrik" ),
        new iso4217_currency_code_s( (short)498, "MDL", "Moldova Leu" ),
        new iso4217_currency_code_s( (short)504, "MAD", "Morocco Dirham" ),
        new iso4217_currency_code_s( (short)512, "OMR", "Oman Rial" ),
        new iso4217_currency_code_s( (short)516, "NAD", "Namibia Dollar" ),
        new iso4217_currency_code_s( (short)524, "NPR", "Nepal Rupee" ),
        new iso4217_currency_code_s( (short)532, "ANG", "Netherlands Antilles Guilder" ),
        new iso4217_currency_code_s( (short)533, "AWG", "Aruba Guilder" ),
        new iso4217_currency_code_s( (short)548, "VUV", "Vanuatu Vatu" ),
        new iso4217_currency_code_s( (short)554, "NZD", "New Zealand Dollar" ),
        new iso4217_currency_code_s( (short)558, "NIO", "Nicaragua Cordoba" ),
        new iso4217_currency_code_s( (short)566, "NGN", "Nigeria Naira" ),
        new iso4217_currency_code_s( (short)578, "NOK", "Norway Krone" ),
        new iso4217_currency_code_s( (short)586, "PKR", "Pakistan Rupee" ),
        new iso4217_currency_code_s( (short)590, "PAB", "Panama Balboa" ),
        new iso4217_currency_code_s( (short)598, "PGK", "Papua New Guinea Kina" ),
        new iso4217_currency_code_s( (short)600, "PYG", "Paraguay Guarani" ),
        new iso4217_currency_code_s( (short)604, "PEN", "Peru Sol" ),
        new iso4217_currency_code_s( (short)608, "PHP", "Philippines Peso" ),
        new iso4217_currency_code_s( (short)634, "QAR", "Qatar Riyal" ),
        new iso4217_currency_code_s( (short)643, "RUB", "Russia Ruble" ),
        new iso4217_currency_code_s( (short)646, "RWF", "Rwanda Franc" ),
        new iso4217_currency_code_s( (short)654, "SHP", "Saint Helena Pound" ),
        new iso4217_currency_code_s( (short)678, "STD", "Sao Tome and Principe dobra" ),
        new iso4217_currency_code_s( (short)682, "SAR", "Saudi Arabia Riyal" ),
        new iso4217_currency_code_s( (short)690, "SCR", "Seychelles Rupee" ),
        new iso4217_currency_code_s( (short)694, "SLL", "Sierra Leone Leone" ),
        new iso4217_currency_code_s( (short)702, "SGD", "Singapore Dollar" ),
        new iso4217_currency_code_s( (short)704, "VND", "Viet Nam Dong" ),
        new iso4217_currency_code_s( (short)706, "SOS", "Somalia Shilling" ),
        new iso4217_currency_code_s( (short)710, "ZAR", "South Africa Rand" ),
        new iso4217_currency_code_s( (short)728, "SSP", "South Sudanese pound" ),
        new iso4217_currency_code_s( (short)748, "SZL", "Swaziland Lilangeni" ),
        new iso4217_currency_code_s( (short)752, "SEK", "Sweden Krona" ),
        new iso4217_currency_code_s( (short)756, "CHF", "Switzerland Franc" ),
        new iso4217_currency_code_s( (short)760, "SYP", "Syria Pound" ),
        new iso4217_currency_code_s( (short)764, "THB", "Thailand Baht" ),
        new iso4217_currency_code_s( (short)776, "TOP", "Tonga Pa‘anga" ),
        new iso4217_currency_code_s( (short)780, "TTD", "Trinidad and Tobago Dollar" ),
        new iso4217_currency_code_s( (short)784, "AED", "United Arab Emirates Dirham" ),
        new iso4217_currency_code_s( (short)788, "TND", "Tunisia Dinar" ),
        new iso4217_currency_code_s( (short)800, "UGX", "Uganda Shilling" ),
        new iso4217_currency_code_s( (short)807, "MKD", "Macedonia Denar" ),
        new iso4217_currency_code_s( (short)818, "EGP", "Egypt Pound" ),
        new iso4217_currency_code_s( (short)826, "GBP", "United Kingdom Pound" ),
        new iso4217_currency_code_s( (short)834, "TZS", "Tanzania Shilling" ),
        new iso4217_currency_code_s( (short)840, "USD", "United States Dollar" ),
        new iso4217_currency_code_s( (short)858, "UYU", "Uruguay Peso" ),
        new iso4217_currency_code_s( (short)860, "UZS", "Uzbekistan Som" ),
        new iso4217_currency_code_s( (short)882, "WST", "Samoa Tala" ),
        new iso4217_currency_code_s( (short)886, "YER", "Yemen Rial" ),
        new iso4217_currency_code_s( (short)901, "TWD", "Taiwan New Dollar" ),
        new iso4217_currency_code_s( (short)931, "CUC", "Cuba Convertible Peso" ),
        new iso4217_currency_code_s( (short)932, "ZWL", "Zimbabwe Dollar" ),
        new iso4217_currency_code_s( (short)933, "BYN", "Belarus Ruble" ),
        new iso4217_currency_code_s( (short)934, "TMT", "Turkmenistan Manat" ),
        new iso4217_currency_code_s( (short)936, "GHS", "Ghana Cedi" ),
        new iso4217_currency_code_s( (short)37, "VEF", "Venezuela Bolívar" ),
        new iso4217_currency_code_s( (short)938, "SDG", "Sudan Pound" ),
        new iso4217_currency_code_s( (short)941, "RSD", "Serbia Dinar" ),
        new iso4217_currency_code_s( (short)943, "MZN", "Mozambique Metical" ),
        new iso4217_currency_code_s( (short)944, "AZN", "Azerbaijan Manat" ),
        new iso4217_currency_code_s( (short)946, "RON", "Romania Leu" ),
        new iso4217_currency_code_s( (short)949, "TRY", "Turkey Lira" ),
        new iso4217_currency_code_s( (short)950, "XAF", "CFA franc BEAC" ),
        new iso4217_currency_code_s( (short)951, "XCD", "East Caribbean Dollar" ),
        new iso4217_currency_code_s( (short)952, "XOF", "CFA franc BCEAO" ),
        new iso4217_currency_code_s( (short)953, "XPF", "CFP franc (franc Pacifique)" ),
        new iso4217_currency_code_s( (short)960, "XDR", "IMF Special Drawing Rights" ),
        new iso4217_currency_code_s( (short)967, "ZMW", "Zambia Kwacha" ),
        new iso4217_currency_code_s( (short)968, "SRD", "Suriname Dollar" ),
        new iso4217_currency_code_s( (short)969, "MGA", "Madagascar Ariary" ),
        new iso4217_currency_code_s( (short)971, "AFN", "Afghanistan Afghani" ),
        new iso4217_currency_code_s( (short)972, "TJS", "Tajikistan Somoni" ),
        new iso4217_currency_code_s( (short)973, "AOA", "Angola Kwanza" ),
        new iso4217_currency_code_s( (short)975, "BGN", "Bulgaria Lev" ),
        new iso4217_currency_code_s( (short)976, "CDF", "Congo/Kinshasa Franc" ),
        new iso4217_currency_code_s( (short)977, "BAM", "Bosnia and Herzegovina Convertible Marka" ),
        new iso4217_currency_code_s( (short)978, "EUR", "Euro" ),
        new iso4217_currency_code_s( (short)980, "UAH", "Ukraine Hryvnia" ),
        new iso4217_currency_code_s( (short)981, "GEL", "Georgia Lari" ),
        new iso4217_currency_code_s( (short)985, "PLN", "Poland Zloty" ),
        new iso4217_currency_code_s( (short)986, "BRL", "Brazil Real" ),
        new iso4217_currency_code_s( (short)0, "---", "Unknown currency")
};
    
    //------------------------------------------------------------------------------------
    
   public static class EMVFunctions extends UFrEMV {
	   
    public  int getSfi(emv_tree_node_t tag_node, byte[] sfi)
    {
        if (!tag_node.is_node_type[0])
            return ERRORCODES.EMV_ERR_TAG_NOT_FOUND.value;

        if (tag_node.tag[0] == 0x88)
        {
            if (tag_node.value_len[0] == 1)
            {
                sfi[0] = (byte)tag_node.value[0];
                return ERRORCODES.UFR_OK.value;
            }
            else
            {
                return ERRORCODES.EMV_ERR_TAG_WRONG_SIZE.value;
            }
        }
        else
        {
            if (tag_node.subnode != null)
            {
                return getSfi(tag_node.subnode, sfi);
            }
            else
            {

                return getSfi(tag_node.next, sfi);
            }
        }
    }

  //--------------------------------------------------------------------

    public  int getAid(emv_tree_node_t tag_node, byte[] aid, byte[] aid_len)
   {
        int status;
        while (tag_node.value_len[0] != 0)
        {
            status = getAid__(tag_node, aid, aid_len);
            if (status == ERRORCODES.UFR_OK.value)
            {
            	return ERRORCODES.UFR_OK.value;
            }
            tag_node = tag_node.next;
        }

        return ERRORCODES.EMV_ERR_TAG_NOT_FOUND.value;
    }

    //--------------------------------------------------------------------

    public  int getAid__(emv_tree_node_t tag_node, byte[] aid, byte aid_len[])
    {
        if (tag_node.value_len[0] == 0)
        {
            return ERRORCODES.EMV_ERR_TAG_NOT_FOUND.value;
        }

        if (tag_node.tag[0] == 0x4F)
        {
            if (tag_node.value_len[0] < 17)
            {

                aid_len[0] = (byte)tag_node.value_len[0];
                //Array.Copy(tag_node.value, aid, tag_node.value_len);
                //TODO: TEST ARRAY COPY!
                System.arraycopy(tag_node.value, 0, aid, 0, tag_node.value_len[0]);
                //aid = tag_node.value.clone();

                return ERRORCODES.UFR_OK.value;
            }
            else
            {
                return ERRORCODES.EMV_ERR_TAG_WRONG_SIZE.value;
            }
        }
        else
        {
            if (tag_node.subnode != null)
            {
                return getAid__(tag_node.subnode, aid, aid_len);
            }
            else
            {
                return getAid__(tag_node.next, aid, aid_len);
            }
        }

    }

    //--------------------------------------------------------------------

    public  int getLogEntry(emv_tree_node_s tag_node, byte[] sfi, byte[] log_records)
    {
        int status;

        while (tag_node.value_len[0] > 0)
        {
            status = getLogEntry__(tag_node, sfi, log_records);
            if (status == ERRORCODES.UFR_OK.value)
            {
                return ERRORCODES.UFR_OK.value;
            }
            tag_node = tag_node.next;
        }
        return ERRORCODES.EMV_ERR_TAG_NOT_FOUND.value;
    }

    //--------------------------------------------------------------------

    public  int getLogEntry__(emv_tree_node_s tag_node, byte sfi[], byte[] log_records)
    {
    	
    	
        if (tag_node.description == "")
        {
            return ERRORCODES.EMV_ERR_TAG_NOT_FOUND.value;
        }
        if (tag_node.tag[0] == (int)0x9F4D)
        {
            if (tag_node.value_len[0] == 2)
            {
                sfi[0] = (byte)tag_node.value[0];
                log_records[0] = (byte)tag_node.value[1];
                return ERRORCODES.UFR_OK.value;
            }
            else
            {
                return ERRORCODES.EMV_ERR_TAG_WRONG_SIZE.value;
            }
        }
        else
        {
            if (tag_node.subnode != null)
            {
                return getLogEntry__(tag_node.subnode, sfi, log_records);
            }
            else
            {
                return getLogEntry__(tag_node.next, sfi, log_records);
            }
        }
    }

    //--------------------------------------------------------------------

    public  int getListLength(emv_tree_node_t tag_node, short[] length)
    {
    
        emv_tree_node_t p = new emv_tree_node_t();

        length[0] = 0;

        if (tag_node.description == "")
            return ERRORCODES.EMV_ERR_TAG_NOT_FOUND.value;
        if (tag_node.tag_type != tag_type_t.TL_LIST)
            return ERRORCODES.EMV_ERR_TAG_WRONG_TYPE.value;
        if (tag_node.tl_list_format.value_len[0] == 0)
        {
            return ERRORCODES.EMV_ERR_LIST_FORMAT_NOT_FOUND.value;
        }

        p = tag_node.tl_list_format;

        while (p != null)
        {
            length[0] += (short)p.value_len[0];
            p = p.next;
        }
        return ERRORCODES.UFR_OK.value;
    }

    //--------------------------------------------------------------------

    public int getAfl(emv_tree_node_t tag_node, afl_list_item_t[] afl_list_item, byte[] afl_list_count)
    {
    	
    	if (tag_node == null)
        {
            return ERRORCODES.EMV_ERR_TAG_NOT_FOUND.value;
        }
    	
        byte items = 0;
        byte[] value_ptr = new byte[tag_node.value_len[0]];
        afl_list_item_t[] temp = new afl_list_item_t[1];
        afl_list_item_t[] p = new afl_list_item_t[1];
     
        int status;

        afl_list_count[0] = 0;

        
        if (tag_node.tag[0] == (int)0x94)
        {
            if ((tag_node.value_len[0] == 0) || ((tag_node.value_len[0] % 4) > 0)) //first 2 bytes are AIP
                return ERRORCODES.EMV_ERR_TAG_WRONG_SIZE.value;
            else
            {
                items = (byte)(tag_node.value_len[0] / 4);                
                value_ptr = tag_node.value;
                System.arraycopy(tag_node.value, 0, value_ptr, 0, tag_node.value_len[0]);


                byte ptr_val = 0;
                while (items > 0)
                {
                   
                    status = newAflListItem(p);
                    if (afl_list_item[0] == null)
                    {
                        if (status > 0)
                        { 
                            return status;
                        }
                        afl_list_item[0] = p[0];
                        temp[0] = p[0];
                    }
                    else
                    {
                        if (status > 0)
                        {
                            emvAflListCleanup(afl_list_item[0]);
                            afl_list_item[0] = null;
                            return status;
                        }
                        temp[0].next = p[0];
                        temp[0] = temp[0].next;
                    }

                    	p[0].sfi[0] = value_ptr[ptr_val++];
                        p[0].sfi[0] >>= 3;
                        p[0].record_first[0] = value_ptr[ptr_val++];
                        p[0].record_last[0] = value_ptr[ptr_val++];
                        p[0].record_num_offline_auth[0] = value_ptr[ptr_val++];

                        items--;
                }

                afl_list_count[0] = (byte)(tag_node.value_len[0] / 4);
                              

                return ERRORCODES.UFR_OK.value;

            }

        } else
        {
            if (tag_node.subnode != null)
            {
                return getAfl(tag_node.subnode, afl_list_item, afl_list_count);
            }
            else
            {
                return getAfl(tag_node.next,  afl_list_item, afl_list_count);
            }
        }
    }

    //--------------------------------------------------------------------
    
    public  int getAflFromResponseMessageTemplateFormat1(emv_tree_node_t tag_node, afl_list_item_t[] afl_list_item, byte[] afl_list_count)
    {
    	
        if (tag_node == null)
            return ERRORCODES.EMV_ERR_TAG_NOT_FOUND.value;
    	
        byte items = 0, len = 0;
        byte[] value_ptr = new byte[tag_node.value_len[0]];
        
        boolean first_item = true;
        afl_list_item_t temp = new afl_list_item_t();
        afl_list_item_t[] p = new afl_list_item_t[1];

        int status;

        afl_list_count[0] = 0;


        
        if (tag_node.tag[0] == 0x80)
        {
            len = (byte)(tag_node.value_len[0] - 2); // first 2 bytes are AIP
            if ((len==0) || ((len % 4) == 0))
            { // first 2 bytes are AIP
                return ERRORCODES.EMV_ERR_TAG_WRONG_SIZE.value;
            }
            else
            {
                items = (byte)(len / 4);
                value_ptr = tag_node.value;  // first 2 bytes are AIP
                byte ptr_val = value_ptr[0];
                while (items > 0)
                {
                    status = newAflListItem(p); // all members are cleared
                    if (first_item)
                    {
                        if (status > 0)
                            return status;
                        afl_list_item[0] = p[0];
                        temp = p[0];
                        first_item = false;
                    }
                    else
                    {
                        if (status > 0)
                        {
                            emvAflListCleanup(afl_list_item[0]);
                            return status;
                        }
                        afl_list_item[0].next = p[0];
                        afl_list_item[0] = afl_list_item[0].next;
                    }

                    p[0].sfi[0] = ptr_val++;
                    p[0].sfi[0] >>= 3;
                    p[0].record_first[0] = ptr_val++;
                    p[0].record_last[0] = ptr_val++;
                    p[0].record_num_offline_auth[0] = ptr_val++;

                    System.arraycopy(value_ptr, 4, value_ptr, 0, value_ptr.length - 4);
                    
                    items--;
                }
                afl_list_count[0] = (byte)(len / 4);
                return ERRORCODES.UFR_OK.value;
            }
        }
        else
        {
            if (tag_node.subnode != null)
            {
                return getAfl(tag_node.subnode, afl_list_item, afl_list_count);
            }
            else
            {
                return getAfl(tag_node.next, afl_list_item, afl_list_count);
            }
        }
    }
    //------------------------------------------------------------------------------

    public  int newAflListItem(afl_list_item_t[] afl_list)
    {
        afl_list_item_t p = new afl_list_item_t();

        if (p == null)
        {
            return ERRORCODES.SYS_ERR_OUT_OF_MEMORY.value;
        } else
        {
            afl_list[0] = p;
        }

        p.sfi[0] = 0;
        p.record_first[0] = 0;
        p.record_last[0] = 0;
        p.record_num_offline_auth[0] = 0;
        p.next = null;

        afl_list[0] = p;

        return ERRORCODES.UFR_OK.value;
    }

    //--------------------------------------------------------------------

    public  void emvAflListCleanup(afl_list_item_s head)
    {
        afl_list_item_s temp;

        while (head.sfi[0] != 0)
        {
            temp = head.next;
            head = temp;
        }
    }

    //--------------------------------------------------------------------

    public  int parseEmvTag(byte[] tag_ptr, int[] tag, byte[] tag_val, int[] tag_len, int tag_len_len[], int[] tag_val_len)
    {
        byte ptr = 0;
        tag[0] = 0x00;
        tag[0] = Byte.toUnsignedInt(tag_ptr[ptr++]);
        
        tag_len[0] = 1;
        if ((tag[0] & 0x1F) == 0x1F)
        {
        	tag[0] <<= 8;
        	tag[0] |= tag_ptr[ptr];
            (tag_len[0])++;
            if ((tag_ptr[ptr++] & 0x80) == 0x80)
            {
            	tag[0] <<= 8;
            	tag[0] |= tag_ptr[ptr];
                (tag_len[0])++;
            }
        }

        //Length
        tag_len_len[0] = 1;
        tag_val_len[0] = tag_ptr[ptr];
        if ((tag_ptr[ptr] & 0x80) == 0x80)
        {
            tag_len_len[0] += tag_ptr[ptr] & 0x7F;
        }
        if (tag_len_len[0] > 3)
        {
            return ERRORCODES.EMV_ERR_MAX_TAG_LEN_BYTES_EXCEEDED.value;
        }

        if (tag_len_len[0] > 1)
        {
            tag_val_len[0] = 0;
            for (int i = tag_len_len[0] - 1; i > 0; i--)
            {
                tag_val_len[0] |= tag_ptr[ptr++] << ((i - 1) * 8);
            }
        }

        ptr++;
        //tag_val = new byte[tag_val_len[0]];

        for (int i = 0; i < tag_val_len[0]; i++)
        {
            tag_val[i] = tag_ptr[ptr];
            ptr++;
        }
        
        return ERRORCODES.UFR_OK.value;

    }
    
    //--------------------------------------------------------------------
    
    public emv_tree_node_t newEmvTag(emv_tree_node_t head, byte[] input, int[] input_bytes_left, boolean is_list_format, int[] status)
    {
    	 emv_tree_node_t p = new emv_tree_node_t(); //OVO GUBI REFERENCU I VRACA MI NULL
         
         int tag_index = 0;
         byte[] tag_val = new byte[1024];
         int[] tag_len = new int[1];
         int[] tag_len_len = new int[1];
         int[] tag_val_len = new int[1];
         int temp = 0;
         boolean[] is_node_type = new boolean[1];
         int[] tag = new int[1];
         byte temp_ptr = 0;
         
         status[0] = parseEmvTag(input, tag, tag_val, tag_len, tag_len_len, tag_val_len);

         if (status[0] != ERRORCODES.UFR_OK.value)
         {
             return null;
         }

         tag_index = findEmvTagIndex(tag[0]);

         is_node_type[0] = (emv_tags[tag_index].tag_type == tag_type_t.NODE);

         temp = tag_len[0] + tag_len_len[0];

         if (!is_node_type[0] && !is_list_format)
         {
             temp += tag_val_len[0];
         }
         
         input_bytes_left[0] -= temp;
         temp_ptr += temp;
         //Array.Copy(value_ptr, 4, value_ptr, 0, value_ptr.Length - 4);
         System.arraycopy(input, temp_ptr, input, 0, input.length - temp_ptr);
         //TODO: Check newemvtag arraycopy method
         
         head = p;

         p.is_node_type[0] = is_node_type[0];
         p.tag[0] = tag[0];
         p.tag_bytes[0] = (byte)tag_len[0];
         p.tag_type = emv_tags[tag_index].tag_type;
         p.description = emv_tags[tag_index].description;
         p.tl_list_format = null;
         p.subnode = null;
        
         p.value_len = tag_val_len;

         if (!(p.is_node_type[0]) && !is_list_format && (tag_val_len[0] > 0))
         {
             if (p.tag_type == tag_type_t.STR)
             {
                 temp = tag_val_len[0] + 1;
             }

             p.value = tag_val;

             if (p.value == null)
             {
                 status[0] = ERRORCODES.SYS_ERR_OUT_OF_MEMORY.value;
                 return null;
             }            
         }

         if (p.tag_type == tag_type_t.TL_LIST)
         {

        	 p.tl_list_format = newEmvTag(p.tl_list_format, p.value, p.value_len, true, status);
         }

         if ((input_bytes_left[0] < 0) || (is_node_type[0] && (input_bytes_left[0] != tag_val_len[0])))
         {
             status[0] = ERRORCODES.EMV_ERR_WRONG_INPUT_DATA.value;
             return null;
         }

         else if (input_bytes_left[0] > 0)
         {
             if (p.is_node_type[0])
             {
            	 p.subnode = newEmvTag(p.subnode, input, input_bytes_left, false, status);
             }
             else
             {
            	 p.next = newEmvTag(p.next, input, input_bytes_left, is_list_format, status);
             }

             if (status[0] != ERRORCODES.UFR_OK.value)
             {
                 return null;
             }

         }
         
        status[0] = 0; 
    	return head;
    }
    
    
    //--------------------------------------------------------------------
    public int findEmvTagIndex(int tag)
    {
        int i = 0;

        do
        {
            if (emv_tags[i].tag == tag)
                break;
            i++;
        } while (emv_tags[i].tag_id_len != 0);

        return i;
    }

    //--------------------------------------------------------------------

    public int emvReadRecord(byte[] r_apdu, int[] Ne, byte sfi, byte record, byte[] sw)
    {
        int status; //DL_STATUS == UFR_STATUS
       
        sfi <<= 3;
        sfi |= 4;
        Ne[0] = 256;
        
        IntByReference Ne_temp = new IntByReference();
        Ne_temp.setValue(Ne[0]);
                
        status = ufr.APDUTransceive((byte)0x00, (byte)0xB2, (byte)record, (byte)sfi, null, 0, r_apdu, Ne_temp, (byte)1, sw);
        if (status != 0)
            return ERRORCODES.EMV_ERR_IN_CARD_READER.value;

        if (sw[0] == 0x6C)
        {
            Ne[0] = sw[1];

            status = ufr.APDUTransceive((byte)0x00, (byte)0xB2, (byte)record, (byte)sfi, null, 0, r_apdu, Ne_temp, (byte)1, sw);
            if (status != 0)
            {
                return ERRORCODES.EMV_ERR_IN_CARD_READER.value;
            }
            else if (sw[0] == 0x8262)
                sw[0] = (byte)0x90;

            if (sw[0] != 0x90)
                return ERRORCODES.EMV_ERR_READING_RECORD.value;

        }
        Ne[0] = Ne_temp.getValue();
        return ERRORCODES.UFR_OK.value;
    }

    //--------------------------------------------------------------------

    public int formatGetProcessingOptionsDataField(emv_tree_node_t tag_node, byte[] gpo_data_field, short[] gpo_data_field_size)
    {
        byte[] temp = new byte[1024];
        emv_tree_node_t[] pdol = new emv_tree_node_t[1];
        emv_tree_node_t p = null;
        int status;

        //gpo_data_field = null;
        gpo_data_field_size[0] = 0;

        status = getPdol(tag_node, pdol);
        if ((status > 0) && status != ERRORCODES.EMV_ERR_TAG_NOT_FOUND.value)
            return status;

        if (status == ERRORCODES.UFR_OK.value && (pdol == null))
            return ERRORCODES.EMV_ERR_PDOL_IS_EMPTY.value;

        if (status != ERRORCODES.EMV_ERR_TAG_NOT_FOUND.value)
        {

            p = pdol[0];
            while (p != null)
            {
                gpo_data_field_size[0] += (short)p.value_len[0];
                p = p.next;
            }

            if (gpo_data_field_size[0] == 0)
                return ERRORCODES.EMV_ERR_PDOL_IS_EMPTY.value;
        }

        gpo_data_field_size[0] += 2;

        //gpo_data_field = new byte[gpo_data_field_size[0]];

        if (gpo_data_field == null)
        {
            gpo_data_field_size[0] = (short)0;
            return ERRORCODES.SYS_ERR_OUT_OF_MEMORY.value;
        }

        (gpo_data_field)[0] = (byte)0x83;
        (gpo_data_field)[1] = (byte)(gpo_data_field_size[0] - 2);

        if (status != ERRORCODES.EMV_ERR_TAG_NOT_FOUND.value)
        {
            p = pdol[0];
            byte gpo_ptr = 2;
            //temp[0] = (byte)(gpo_data_field[gpo_ptr] + 2);
            
            //System.arraycopy(gpo_data_field, 2, temp, 0, gpo_data_field.length - 2);
            while (p != null)
            {
                if (p.tag[0] == 0x9F66) // Terminal Transaction Qualifiers (TTQ) Tag
                {
                    //temp[0] = 0x28;
                    gpo_data_field[gpo_ptr] = (byte)0x28;
                    
                    //				temp[1] = 0x20;
                    //				temp[2] = 0xC0;
                    //				temp[3] = 0x00;
                }
                else if (p.tag[0] == 0x5F2A) //
                {
                    //temp[0] = 0x09;
                    gpo_data_field[gpo_ptr] = (byte)0x09;
                    //temp[1] = 0x41;
                    gpo_data_field[gpo_ptr + 1] = (byte)0x41;
                }
                else if (p.tag[0] == 0x9A03)
                {
                    //temp[0] = 0x17;
                    gpo_data_field[gpo_ptr] = (byte)0x17;
                    //temp[1] = 0x08;
                    gpo_data_field[gpo_ptr + 1] = (byte)0x08;
                    //temp[2] = 0x15;
                    gpo_data_field[gpo_ptr + 2] = (byte)0x15;
                }

                gpo_ptr += p.value_len[0];
                //System.arraycopy(temp, srcPos, dest, destPos, length);
                p = p.next;
            }
            
            //System.arraycopy(temp, 0, gpo_data_field, 2, temp.length - 2);
        }

        return ERRORCODES.UFR_OK.value;
    }
    //--------------------------------------------------------------------
    public int getPdol(emv_tree_node_t tag_node, emv_tree_node_t[] pdol)
    {
        if (tag_node == null)
            return ERRORCODES.EMV_ERR_TAG_NOT_FOUND.value;

        if (tag_node.tag[0] == 0x9f38)
        {
            if (tag_node.value_len != null)
            {
                pdol[0] = tag_node.tl_list_format;
                return ERRORCODES.UFR_OK.value;
            }
            else
            {
                return ERRORCODES.EMV_ERR_TAG_WRONG_SIZE.value;
            }
        }
        else
        {
            if (tag_node.subnode != null)
            {
                return getPdol(tag_node.subnode, pdol);
            }
            else
            {
                return getPdol(tag_node.next, pdol);
            }
        }
    }
    //--------------------------------------------------------------------
    public boolean isExistATCounter( emv_tree_node_t log_list_item_format, short[] pos, short[] len)
    {
        pos[0] = 0;
        while (log_list_item_format != null)
        {
            if (log_list_item_format.tag[0] == 0x9f36)
            {
                len[0] = (short)log_list_item_format.value_len[0];
                return true;
            }
            pos[0] += (short)log_list_item_format.value_len[0];
            log_list_item_format = log_list_item_format.next;
        }
        return false;
    }
    //------------------------------------------------------------------------------
    public boolean isExistTransactionDate(emv_tree_node_t log_list_item_format, short[] pos, short[] len)
    {

        pos[0] = 0;

        while (log_list_item_format != null)
        {
            if (log_list_item_format.tag[0] == 0x9a)
            {
                len[0] = (short)log_list_item_format.value_len[0];
                return true;
            }
            pos[0] += (short)log_list_item_format.value_len[0];
            log_list_item_format = log_list_item_format.next;
        }
        return false;
    }
    //------------------------------------------------------------------------------
    public boolean isExistTransactionTime( emv_tree_node_t log_list_item_format, short[] pos, short[] len)
    {
        pos[0] = 0;
        while (log_list_item_format != null)
        {
            if (log_list_item_format.tag[0] == 0x9f21)
            {
                len[0] = (short)log_list_item_format.value_len[0];
                return true;
            }
            pos[0] += (short)log_list_item_format.value_len[0];
            log_list_item_format = log_list_item_format.next;
        }
        return false;
    }
    //------------------------------------------------------------------------------
    public boolean isExistAmountAuthorised( emv_tree_node_t log_list_item_format, short pos[], short[] len)
    {
        pos[0] = 0;
        while (log_list_item_format != null)
        {
            if (log_list_item_format.tag[0] == 0x9f02)
            {
                len[0] = (short)log_list_item_format.value_len[0];
                return true;
            }
            pos[0] += (short)log_list_item_format.value_len[0];
            log_list_item_format = log_list_item_format.next;
        }
        return false;
    }
    //------------------------------------------------------------------------------
    public boolean isExistTransactionCurrency( emv_tree_node_t log_list_item_format, short[] pos, short[] len)
    {
        pos[0] = 0;
        while (log_list_item_format != null)
        {
            if (log_list_item_format.tag[0] == 0x5f2a)
            {
                len[0] = (short)log_list_item_format.value_len[0];
                return true;
            }
            pos[0] += (short)log_list_item_format.value_len[0];
            log_list_item_format = log_list_item_format.next;
        }
        return false;
    }
    //------------------------------------------------------------------------------
    public boolean isExistTerminalCountry(emv_tree_node_t log_list_item_format, short[] pos, short[] len)
    {
        pos[0] = 0;
        while (log_list_item_format != null)
        {
            if (log_list_item_format.tag[0] == 0x9f1a)
            {
                len[0] = (short)log_list_item_format.value_len[0];
                return true;
            }
            pos[0] += (short)log_list_item_format.value_len[0];
            log_list_item_format = log_list_item_format.next;
        }
        return false;
    }
    //--------------------------------------------------------------------
     public long bin_bcd_to_ll(byte[] bin, short[] amountAuthorised_len)
    {
        long result = 0;
        long dec = 1;
        int len = Short.toUnsignedInt(amountAuthorised_len[0]);

        for (int i = len; i > 0; i--)
        {
            result += (long)(bin[i - 1] & 0x0F) * dec;
             dec *= 10;
            result += (long)(bin[i - 1] >> 4) * dec;
            dec *= 10;
        }

        return result;


    }
     
     
   //------------------------------------------------------------------------------
   
   public String findCurrencyIndexByNumCode(short num_code)
   {
        int i = 0;

        do
        {
            if (iso4217_currency_codes[i].num_code == num_code)
                break;
            i++;
        } while (iso4217_currency_codes[i].num_code != 0);

        return iso4217_currency_codes[i].alpha_code;
   }
    
    //---------------------------------------------------------------------------------------------

    public  byte[] ToByteArray(String HexString)
    {

        int NumberChars = HexString.length();
        byte[] bytes = new byte[NumberChars / 2];

        if (HexString.length() % 2 != 0)
        {
            return bytes;
        }

        for (int i = 0; i < NumberChars; i += 2)
        {
            try
            {
                //bytes[i / 2] = Convert.ToByte(HexString.Substring(i, 2), 16);
                bytes[i / 2] = (byte)Integer.parseInt(HexString.substring(i, i + 2), 16);
            }
            catch (Exception e)
            {
                //System.Windows.Forms.MessageBox.Show("Incorrect format!");
            	
                System.out.println(e.getMessage());
            	break;
            }
        }

        return bytes;
    }
    
   };
    
}
