package solarcar.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import solarcar.backend.Buckets.Velocity;
import solarcar.backend.services.DB;

@RestController
public class RestAPI {
    @Autowired
    private final DB db;

    public RestAPI(DB db) {
        this.db = db;
    }

//    @GetMapping("/dcbus")
//    public Velocity velocity(){
//        return db.getDCBus();
//    }
//    @GetMapping("/drivecmd")
//    public Velocity velocity(){
//        return db.getDriveCMD();
//    }
//    @GetMapping("/mainpack")
//    public Velocity velocity(){
//        return db.getMainPack();
//    }
//    @GetMapping("/odobusamp")
//    public Velocity velocity() { return db.getOdoBusAmp(); }
//    @GetMapping("/packtemp")
//    public Velocity velocity(){
//        return db.getPackTemp();
//    }
    @GetMapping("/velocity")
    public Velocity velocity(){
        return db.getVelocity();
    }
//    @GetMapping("/voltageinfo")
//    public Velocity velocity(){
//        return db.getVoltageInfo();
//    }
}

