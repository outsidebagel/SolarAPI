package solarcar.backend.services;


import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.QueryApi;
import com.influxdb.client.domain.Buckets;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class DB {
    private final InfluxDBClient db;
    private final QueryApi query;

    @Value("${influx.url}")
    private String url;

    @Value("${influx.apiKey}")
    private char[] apiKe;

    @Value("${influx.orgname}")
    private String orgname;

    @Value("${influx.bucket}")
    private String bucket;


    public DB() {
        this.db = InfluxDBClientFactory.create(url, apiKey, orgname, bucket);
        this.query = db.getQueryApi();
    }

    // Query the database for new data, only
    public ArrayList<Object> getNewData() {
        // stores our data to return
        ArrayList<Object> returnList = new ArrayList<>();

        // get our latest data from our db
        // this query will return latest tables for each of our fields
        String flux = "from(bucket: \"Cardata\")\n" +
                "  |> range(start: -1h)\n" +
                "  |> filter(fn: (r) => r[\"_measurement\"] == \"odoAndAmp\" or r[\"_measurement\"] == \"velocity\" or r[\"_measurement\"] == \"packVoltInfo\" or r[\"_measurement\"] == \"packTempInfo\" or r[\"_measurement\"] == \"mainPackInfo\" or r[\"_measurement\"] == \"dcBus\" or r[\"_measurement\"] == \"driveCMD\")\n" +
                "  |> filter(fn: (r) => r[\"_field\"] == \"velMotorRPM\" or r[\"_field\"] == \"packAmp\" or r[\"_field\"] == \"summedV\" or r[\"_field\"] == \"vel\" or r[\"_field\"] == \"motorCurrent\" or r[\"_field\"] == \"odo\" or r[\"_field\"] == \"motorRPM\" or r[\"_field\"] == \"hiCellV\" or r[\"_field\"] == \"highTemp\" or r[\"_field\"] == \"instVolt\" or r[\"_field\"] == \"lowCellV\" or r[\"_field\"] == \"lowTemp\")\n" +
                "  |> last()";

        // send the query
        List<FluxTable> tables = query.query(flux);

        // loop through every table from the query
        for (FluxTable table : tables){
            List<FluxRecord> records = table.getRecords();
            // for each record (there is only one in each table in our current query) add
            for (FluxRecord record : records){
                returnList.add(new CarDataObject(record.getField(), (double) record.getValue()));
            }


        }

        return returnList;


    }

    // our key/value pair object to return our data
    private record CarDataObject(String field, double value) {}

}









