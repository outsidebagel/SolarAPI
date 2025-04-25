package solarcar.backend.services;


import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.QueryApi;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import solarcar.backend.Buckets.Velocity;

import java.time.Instant;
import java.util.List;

@Service
public class DB {
    private final InfluxDBClient db;
    private final QueryApi query;

    @Value("${influx.url}")
    private String url;

    @Value("${influx.apiKey}")
    private char[] apiKey;

    @Value("${influx.orgname}")
    private String orgname;

    @Value("${influx.bucket}")
    private String bucket;



    public DB() {
        this.db = InfluxDBClientFactory.create(url, apiKey, orgname, bucket);
        this.query = db.getQueryApi();
    }


//    public List<Object> getDCBus() {
//        //get our tables from CarData
//        String flux = "from(bucket: \"CarData\")\n" +
//                "    |> range(start: -1m)\n" +
//                "    |> last()";
//    }
//
//    public List<Object> getDriveCMD() {
//        //get our tables from CarData
//        String flux = "from(bucket: \"CarData\")\n" +
//                "    |> range(start: -1m)\n" +
//                "    |> last()";
//    }
//
//    public List<Object> getMainPack() {
//        //get our tables from CarData
//        String flux = "from(bucket: \"CarData\")\n" +
//                "    |> range(start: -1m)\n" +
//                "    |> last()";
//    }
//
//    public List<Object> getOdoBusAmp() {
//        //get our tables from CarData
//        String flux = "from(bucket: \"CarData\")\n" +
//                "    |> range(start: -1m)\n" +
//                "    |> last()";
//    }
//
//    public List<Object> getPackTemp() {
//        //get our tables from CarData
//        String flux = "from(bucket: \"CarData\")\n" +
//                "    |> range(start: -1m)\n" +
//                "    |> last()";
//    }

    public Velocity getVelocity() {
        //get our last velocity data
        String flux = "from(bucket: \"Cardata\")\n" +
                "  |> range(start: -1m)\n" +
                "  |> filter(fn: (r) => r[\"_measurement\"] == \"velocity\")\n" +
                "  |> filter(fn: (r) => r[\"_field\"] == \"vel\")\n" +
                "  |> last()";
        List<FluxTable> tables = query.query(flux);

        //get our lsat motor rpm data

        return null;




    }
//    public List<Object> getVoltageInfo() {
//        //get our tables from CarData
//        String flux = "from(bucket: \"CarData\")\n" +
//                "    |> range(start: -1m)\n" +
//                "    |> last()";
//    }

}
