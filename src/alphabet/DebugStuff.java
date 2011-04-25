package alphabet;

import basic.MonoCipher;

public class DebugStuff {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MonoCipher cipher = new MonoCipher();
		String alph = "hijklmnopqrstuvwxyzabcdefg";
		
		//String ctext = "OVSTL ZOHKI LLUZL HALKM VYZVT LOVBY ZPUZP SLUJL DPAOO PZSVU NAOPU IHJRJ BYCLK VCLYH JOLTP JHSCL ZZLSP UDOPJ OOLDH ZIYLD PUNHW HYAPJ BSHYS FTHSV KVYVB ZWYVK BJAOP ZOLHK DHZZB URBWV UOPZI YLHZA HUKOL SVVRL KMYVT TFWVP UAVMC PLDSP RLHZA YHUNL SHURI PYKDP AOKBS SNYHF WSBTH NLHUK HISHJ RAVWR UVA";
		//String ptext = "holmes had been seated for some hours in silence with his long thin back curved over a chemical vessel in which he was brew ing a particularly malodorous product his head was sunk upon his breast and he looked from my point of view like a strange lankbird with dull gray plumage and a black top knot";

		//String ptext = "Miss Watson she kept pecking at me, and it got tiresome and lonesome.  Byand by they fetched the niggers in and had prayers, and then everybodywas off to bed.  I went up to my room with a piece of candle, and put iton the table.  Then I set down in a chair by the window and tried tothink of something cheerful, but it warn't no use.  I felt so lonesome Imost wished I was dead.  The stars were shining, and the leaves rustledin the woods ever so mournful; and I heard an owl, away off, who-whooingabout somebody that was dead, and a whippowill and a dog crying aboutsomebody that was going to die; and the wind was trying to whispersomething to me, and I couldn't make out what it was, and so it made thecold shivers run over me. Then away out in the woods I heard that kind ofa sound that a ghost makes when it wants to tell about something that'son its mind and can't make itself understood, and so can't rest easy inits grave, and has to go about that way every night grieving.  I got sodown-hearted and scared I did wish I had some company.  Pretty soon aspider went crawling up my shoulder, and I flipped it off and it lit inthe candle; and before I could budge it was all shriveled up.  I didn'tneed anybody to tell me that that was an awful bad sign and would fetchme some bad luck, so I was scared and most shook the clothes off of me.I got up and turned around in my tracks three times and crossed my breastevery time; and then I tied up a little lock of my hair with a thread tokeep witches away.  But I hadn't no confidence.  You do that when you'velost a horseshoe that you've found, instead of nailing it up over thedoor, but I hadn't ever heard anybody say it was any way to keep off badluck when you'd killed a spider";
		String ctext = "TPZZD HAZVU ZOLRL WAWLJ RPUNH ATLHU KPANV AAPYL ZVTLH UKSVU LZVTL IFHUK IFAOL FMLAJ OLKAO LUPNN LYZPU HUKOH KWYHF LYZHU KAOLU LCLYF IVKFD HZVMM AVILK PDLUA BWAVT FYVVT DPAOH WPLJL VMJHU KSLHU KWBAP AVUAO LAHIS LAOLU PZLAK VDUPU HJOHP YIFAO LDPUK VDHUK AYPLK AVAOP URVMZ VTLAO PUNJO LLYMB SIBAP ADHYU AUVBZ LPMLS AZVSV ULZVT LPTVZ ADPZO LKPDH ZKLHK AOLZA HYZDL YLZOP UPUNH UKAOL SLHCL ZYBZA SLKPU AOLDV VKZLC LYZVT VBYUM BSHUK POLHY KHUVD SHDHF VMMDO VDOVV PUNHI VBAZV TLIVK FAOHA DHZKL HKHUK HDOPW WVDPS SHUKH KVNJY FPUNH IVBAZ VTLIV KFAOH ADHZN VPUNA VKPLH UKAOL DPUKD HZAYF PUNAV DOPZW LYZVT LAOPU NAVTL HUKPJ VBSKU ATHRL VBADO HAPAD HZHUK ZVPAT HKLAO LJVSK ZOPCL YZYBU VCLYT LAOLU HDHFV BAPUA OLDVV KZPOL HYKAO HARPU KVMHZ VBUKA OHAHN OVZAT HRLZD OLUPA DHUAZ AVALS SHIVB AZVTL AOPUN AOHAZ VUPAZ TPUKH UKJHU ATHRL PAZLS MBUKL YZAVV KHUKZ VJHUA YLZAL HZFPU PAZNY HCLHU KOHZA VNVHI VBAAO HADHF LCLYF UPNOA NYPLC PUNPN VAZVK VDUOL HYALK HUKZJ HYLKP KPKDP ZOPOH KZVTL JVTWH UFWYL AAFZV VUHZW PKLYD LUAJY HDSPU NBWTF ZOVBS KLYHU KPMSP WWLKP AVMMH UKPAS PAPUA OLJHU KSLHU KILMV YLPJV BSKIB KNLPA DHZHS SZOYP CLSLK BWPKP KUAUL LKHUF IVKFA VALSS TLAOH AAOHA DHZHU HDMBS IHKZP NUHUK DVBSK MLAJO TLZVT LIHKS BJRZV PDHZZ JHYLK HUKTV ZAZOV VRAOL JSVAO LZVMM VMTLP NVABW HUKAB YULKH YVBUK PUTFA YHJRZ AOYLL APTLZ HUKJY VZZLK TFIYL HZALC LYFAP TLHUK AOLUP APLKB WHSPA ASLSV JRVMT FOHPY DPAOH AOYLH KAVRL LWDPA JOLZH DHFIB APOHK UAUVJ VUMPK LUJLF VBKVA OHADO LUFVB CLSVZ AHOVY ZLZOV LAOHA FVBCL MVBUK PUZAL HKVMU HPSPU NPABW VCLYA OLKVV YIBAP OHKUA LCLYO LHYKH UFIVK FZHFP ADHZH UFDHF AVRLL WVMMI HKSBJ RDOLU FVBKR PSSLK HZWPK LY";
		
		String ptext = cipher.decrypt(alph, ctext);
			
		Fitness f = new Fitness(cipher, ctext);
		
		System.out.println(f.fitness("hijklmnopqrstuvwxyzabcdefg"));
	}

}
