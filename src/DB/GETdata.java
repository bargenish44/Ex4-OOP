package DB;

import java.sql.ResultSet;

public class GETdata {
	DataBase dataBase;
	static int requestCounter = 0;

	public GETdata(DataBase connector) {
		this.dataBase = connector;
	}

	public double avScoreForAll() {
		return avScore("SELECT AVG(Point) FROM logs;");
	}

	public double avScoreForAll(long id) {
		return avScore(
				"SELECT AVG(Point) FROM logs WHERE FirstID=" + id + " OR SecondID=" + id + " OR ThirdID=" + id + ";");
	}

	public double avScore(int scenario) {
		return avScore("SELECT AVG(Point) FROM logs WHERE SomeDouble=" + scenario + ";");
	}

	public double avScore(int scenario, long id) {
		return avScore("SELECT AVG(Point) FROM logs WHERE SomeDouble=" + scenario + " AND ( FirstID=" + id
				+ " OR SecondID=" + id + " OR ThirdID=" + id + ");");
	}

	private double avScore(String statement) {
		double avg = Double.MAX_VALUE;
		try {
			if (dataBase.connectToDB()) {

				ResultSet resultSet = dataBase.getDataFromDB(statement);

				if (resultSet.next()) {
					avg = resultSet.getFloat(1);
				}
				if (!dataBase.disconnectFromDB())
					System.out.println("Failed to disconnect from server...");
			} else
				System.out.println("Failed to connect to server...");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Crashed");
		}
		return avg;
	}
}
