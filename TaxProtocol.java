
public class TaxProtocol {

	private int cols = 4;
	private int rows = 10;
	private int[][] taxRanges = new int[rows][cols];
	private String lowerRange, upperRange, taxPayable, taxOver;
	private int rangeCounter = 1;
	
	public TaxProtocol()
	{
		for(int i = 0; i < rows; i++)
		{
			for(int j = 0; j < cols; j++)
			{
				taxRanges[i][j] = -1;
			}
		}
	}
	
	public String setRange(String value)
	{
		if(rangeCounter == 1)
		{
			lowerRange = value;
			rangeCounter = 2;
			return "";
		}
		else if(rangeCounter == 2)
		{
			upperRange = value;
			rangeCounter = 3;
			return "";
		}
		else if(rangeCounter == 3)
		{
			taxPayable = value;
			rangeCounter = 4;
			return "";
		}
		else if(rangeCounter == 4)
		{
			taxOver = value;
			rangeCounter = 1;
			return processStore(lowerRange, upperRange, taxPayable, taxOver);
		}
		else
		{
			return "";
		}
	}
	
	public String processMessage(String message)
	{
		
		if(message.toLowerCase().equals("tax"))
		{
			return "TAX: OK\n";
		}
		else if(message.toLowerCase().equals("query"))
		{
			
			return getQuery();
		}	
		else if(message.toLowerCase().equals("bye"))
		{
			return "BYE: OK\n";
		}	
		else if(message.toLowerCase().equals("end"))
		{
			return "END: OK\n";
		}	
		
		return "";
	}
	
	private String getQuery()
	{
		String query = "";
		for(int i = 0; i < rows; i++)
		{
			if(taxRanges[i][0] != -1)
			{
				for(int j = 0; j < cols; j++)
				{
					query += taxRanges[i][j] + " ";
				}
				query += "\n";
			}
		}
		query += "QUERY: OK\n";
		return query;
	}
	
	public String processStore(String low, String high, String taxPay, String taxOv)
	{
		boolean entered = false;
		for(int i = 0; i < rows; i++)
		{
			if(entered == false && taxRanges[i][0] == -1)
			{
				try
				{
				taxRanges[i][0] = Integer.parseInt(low.replaceAll("\n", ""));
				//System.out.println(lowerRange.replaceAll("\n",""));
				taxRanges[i][1] = Integer.parseInt(high.replaceAll("\n", ""));
				//System.out.println(upperRange.replaceAll("\n",""));
				taxRanges[i][2] = Integer.parseInt(taxPay.replaceAll("\n", ""));
				//System.out.println(taxPayable.replaceAll("\n",""));
				taxRanges[i][3] = Integer.parseInt(taxOv.replaceAll("\n", ""));
				//System.out.println(taxOver.replaceAll("\n",""));
				entered = true;
				}
				catch(Exception e)
				{
					System.out.println("error");
				}
			}
		}
		
		return "STORE: OK\n";
	}
	
}
