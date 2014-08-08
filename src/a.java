import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;


public class a {
	
    public static void main ( String[] arguments )
    {
    	test1();
    }
    
	public static void test1(){
		MathContext mc = new MathContext(3, RoundingMode.UP);
		
		BigDecimal bd = new BigDecimal("123");
		BigDecimal bd2 = new BigDecimal("2");
		BigDecimal bd3 = new BigDecimal("0.9919");
		BigDecimal bd4 = bd.multiply(bd2).multiply(new BigDecimal("1").subtract(bd3));
		//bd4 = bd4.multiply(new BigDecimal("99.19"));
		
		//System.out.println(bd.round(mc).toPlainString());
		//System.out.println(bd.setScale(9, RoundingMode.UP).toPlainString());
		System.out.println(bd4.setScale(20).toPlainString());
		
		
		java.lang.Double Total = 123 * (1-0.9919) *2;
		System.out.println(Total);
		
		Total = 123 * (100-99.19) *2;
		System.out.println(Total/100);

	}
	

}
