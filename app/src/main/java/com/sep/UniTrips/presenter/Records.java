package com.sep.UniTrips.presenter;

import java.util.ArrayList;
import java.util.List;

public class Records {
    public String station_info; // 车站相关信息
    public List<double[]> coords_double; // 到达车站的坐标路径


    public Records(String station_info, List<double[]> coords_double) {
        this.station_info = station_info;
        this.coords_double = coords_double;
    }


    public static String coords_double_to_string (List<double []> coords) {
        StringBuilder sb = new StringBuilder();
        for(double [] coord : coords) {
            sb.append(coord[0]).append("+").append(coord[1]).append(" ");
        }
        return sb.toString();
    }

    public static List<double[]> coords_string_to_double (String coords_string) {
        List<double[]> list = new ArrayList<double[]>();
        String [] coords = coords_string.split(" ");
        for (String coord : coords) {
            String [] strs = coord.split("\\+");

            double [] coord_double = new double[2];
            coord_double[0] = Double.parseDouble(strs[0]);
            coord_double[1] = Double.parseDouble(strs[1]);
            list.add(coord_double);
        }
        return list;

    }
}
