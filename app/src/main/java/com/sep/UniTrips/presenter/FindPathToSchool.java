package com.sep.UniTrips.presenter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.net.HttpURLConnection;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.TimeZone;

public class FindPathToSchool {

    private double [] locationOfSchool = new double [] {-33.884080, 151.206290};
    private double [] currentLocation = null; //new double [] {-33.9661649,151.1030075};
    private String userPreferredTransport = null;
    private String line = null; // the json line
    private Map<String, List<Records>> map = null;

    private static final String apiKey = "apikey Ys7NXvCei8c0FHRBx0IaPcUeX5Rh0MLYb3JS";


    public FindPathToSchool(String userPreferredTransport) {
        this.userPreferredTransport = userPreferredTransport;
    };
    /**
     * refresh current location based on the GPS
     */
    private void refreshCurrentLocation(double [] currentLocation) {
        this.currentLocation = currentLocation;
    }

    /**
     * set the location of school
     * @param schoolLocation the (longitude, latitude) tuple
     */
    private void setSchoolLocation (double [] schoolLocation) {

        locationOfSchool = schoolLocation;
    }

    /**
     * query the NSW transport API to get the journeys between the current location (starting point)
     * and destination.
     * @return the json string of the journey information
     */
    private String findTrips () {

        BufferedReader reader = null;
        InputStream in = null;
        HttpURLConnection connection = null;

        try {

            String basicUrlString = "https://api.transport.nsw.gov.au/v1/tp/trip?";

            // the starting coordination
            double longitute = currentLocation[1];
            double latitude = currentLocation[0];
            String startCoord = String.format("%01.6f:%01.6f:EPSG:4326", longitute, latitude);

            // the destination coordination
            double longitute2 = locationOfSchool[1];
            double latitude2 = locationOfSchool[0];
            String destCoord = String.format("%01.6f:%01.6f:EPSG:4326", longitute2, latitude2);

            // get the current time and date
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd HHmm");
            Date dateObj = new Date();
            String dateAndTime = df.format(dateObj).toString();

            String date = dateAndTime.split(" ")[0];
            String time = dateAndTime.split(" ")[1];
            //System.out.println("[Debug in FindPathToSchool.findTrips]: " + "date: " + date + " time: " + time);

            /*
            'outputFormat' => 'rapidJSON', 'coordOutputFormat' => 'EPSG:4326', 'depArrMacro' => 'dep',
            'itdDate' => date('Ymd', $when), 'itdTime' => date('Hi', $when), 'type_origin' => 'stop',
             'name_origin' => $origin,
            'type_destination' => 'stop', 'name_destination' => $destination, 'TfNSWTR' => 'true'
            */
            String urlString = new StringBuilder (basicUrlString)
                    .append("outputFormat=rapidJSON").append("&")
                    .append("coordOutputFormat=EPSG:4326").append("&")
                    .append("depArrMacro=dep").append("&")
                    .append("itdDate=").append(date).append("&")
                    .append("itdTime=").append(time).append("&")
                    .append("type_origin=coord").append("&")
                    .append("name_origin=").append(startCoord).append("&")
                    .append("type_destination=coord").append("&")
                    .append("name_destination=").append(destCoord).append("&")
                    .append("TfNSWTR=true").toString();

            URL url = new URL(urlString);
            // get the connection object
            connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", apiKey);

            connection.setConnectTimeout(30000);
            connection.setReadTimeout(30000);

            // get the response
            in = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(in));
            String str;
            StringBuilder sb = new StringBuilder();
            while ((str = reader.readLine()) != null)
                sb.append(str);

            line = sb.toString();

            in.close();

        } catch (MalformedURLException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }
        System.out.println("[Debug in FindPathToSchool.findTrips]: "+ "*************" + line + "*********************");

        return line;
    }

    /**
     * parse the json trip information
     * @param line the json string holding the information
     * @return the map structure is like map{transportation_str: list{result string}}. One transportation may refers to
     *  multiple trip plan.
     */
    private Map<String, List<Records>> parseTrips (String line) {

        // the results of journeys. Key is the transportation method: bus, train, ferry, light rail
        // and the value is the formatted station and time string, like: burwood platform 1 10:00-10:20
        map = new HashMap<String, List<Records>>();

        try {

            JSONObject json = new JSONObject(line);
            JSONArray journeysArr = json.getJSONArray("journeys");
            for(int i = 0; i < journeysArr.length(); i++) {
                // the specific journey
                JSONObject journey = journeysArr.getJSONObject(i);

                JSONArray legs = journey.getJSONArray("legs");
                int size = legs.length();

                // the first partition of the whole journey
                JSONObject firstLeg = legs.getJSONObject(0);
                // the second partition of the journey
                JSONObject secondLeg = legs.getJSONObject(1);

                // the last partition of the journey
                JSONObject lastLeg = legs.getJSONObject(size-1);
                JSONObject secondLastLeg = legs.getJSONObject(size-2);

                // the departs leg
                JSONObject departsLeg = null;
                JSONObject arriveLeg = null;

                // the transportation for the first partition
                int transportationOfFirstLeg = firstLeg.getJSONObject("transportation")
                        .getJSONObject("product").getInt("class");

                // the transportation for the last partition
                int transportationOfLastLeg = lastLeg.getJSONObject("transportation")
                        .getJSONObject("product").getInt("class");

                if (transportationOfFirstLeg == 100 || transportationOfFirstLeg == 99) {
                    // that means the first leg is by walking, and we need to deal with the second partition
                    departsLeg= secondLeg;
                } else { // the first leg is by some public transportation,which is we need
                    departsLeg = firstLeg;
                }

                if (transportationOfLastLeg == 100 || transportationOfLastLeg == 99) {
                    // that means the last leg is by walking, and we need to deal with the second last partition
                    arriveLeg= secondLastLeg;
                } else { // the last leg is by some public transportation,which is we need
                    arriveLeg = lastLeg;
                }

                // the real transportation method for the first partition. We'd use it as the transportation of
                // the whole journey
                int transportation = departsLeg.getJSONObject("transportation")
                        .getJSONObject("product").getInt("class");

                /*
                case 1: $summary[] = 'Train'; break; case 4: $summary[] = 'Light Rail'; break; case 5: $summary[] = 'Bus'; break; case 7: $summary[] = 'Coach'; break;
                    Trip Planner APIs: Technical Documentation 16
                case 9: $summary[] = 'Ferry'; break; case 11: $summary[] = 'School Bus'; break;
                 */
                String transportation_str = null;
                switch (transportation)
                {
                    case 1: transportation_str = "Train"; break;
                    case 4: transportation_str = "Light Rail"; break;
                    case 5: transportation_str = "Bus"; break;
                    case 9: transportation_str = "Ferry"; break;
                    default: transportation_str = "Other";
                }

                // the departs station
                String departsStation = departsLeg.getJSONObject("origin").getString("name");
                String [] departsSplits = departsStation.split(",");
                String departsInfo = null;
                if (departsSplits.length >= 3) {

                    StringBuilder sb = new StringBuilder();

                    for (int j = 1; j < departsSplits.length; j++) {
                        sb.append(departsSplits[j]).append(" ");
                    }
                    departsInfo = sb.toString();
                } else {
                    departsInfo = departsStation;
                }

                // the departs time
                String departsTime = departsLeg.getJSONObject("origin").getString("departureTimePlanned");
                String [] departMinSec = departsTime.split("T")[1].split(":");
                int departMinSec_syd = Integer.parseInt(departMinSec[0]) + 11;
                String departMinSec_syds = Integer.toString(departMinSec_syd);
                String departsTimeInfo = departMinSec_syds + ":" + departMinSec[1];

                // the arrive time
                String arriveTime = arriveLeg.getJSONObject("destination").getString("arrivalTimeEstimated");
                String [] arriveMinSec = arriveTime.split("T")[1].split(":");
                int arriveMinSec_syd = Integer.parseInt(arriveMinSec[0]) + 11;
                String arriveMinSec_syds = Integer.toString(arriveMinSec_syd);
                String arriveTimeInfo = arriveMinSec_syds + ":" + arriveMinSec[1];

                String result_str = departsInfo + " " + departsTimeInfo + "-" + arriveTimeInfo;
                System.out.println(result_str);

                // get the coords of positions from starting point to station
                JSONArray coords = firstLeg.getJSONArray("coords");
                List<double []> coords_double = new ArrayList<double[]>();
                for(int k = 0; k < coords.length(); k++) {
                    JSONArray coord =  coords.getJSONArray(k);
                    double [] coord_double = new double[2];
                    coord_double[0] = coord.getDouble(0);
                    coord_double[1] = coord.getDouble(1);
                    //System.out.println("coord0: " + coord_double[0] + " coord1: " + coord_double[1]);
                    coords_double.add(coord_double);
                }

                Records records = new Records(result_str, coords_double);
                // list1 holds the possible result strings.
                // For example: there may be two train plans to school "Burwood platform 1 10-11" and
                // "Buwood platform 2 10-11"
                List list1 = map.get(transportation_str);
                if (list1 == null) {
                    list1 = new ArrayList<Records>();
                }
                list1.add(records);
                map.put(transportation_str, list1);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return map;
    }




    private Records filterTrip() {

        if (map.isEmpty()) { // no trips found
            return null;
        }

        List<Records> trips = map.get(userPreferredTransport); //get trips via specific transportation manner

        if(trips == null || trips.size() == 0) {
            // the trips for the specific transportation is empty, randomly return other trip available.
            String transport1 = null;
            Records records = null;
            for (Map.Entry<String, List<Records>> entry: map.entrySet()) {
                transport1 = entry.getKey();
                records = entry.getValue().get(0);
                break;
            }

            return records;
        } else {
            return trips.get(0); //the trips is not empty, just return the first record
        }

    }


    /**
     * It's the key method for this class, which is used by HomeFragement
     * @param userPreferredTransport the specific transportation manner
     * @return the information of trip
     */
    public static Records getTripInformation (double [] currentLocation, String userPreferredTransport) {

        FindPathToSchool findPathToSchool = new FindPathToSchool(userPreferredTransport);
        // refresh current location
        findPathToSchool.refreshCurrentLocation(currentLocation);

        // query the NSW transport api, and get the json text
        String line = findPathToSchool.findTrips();

        // parse the json text into trip information, and store the information in a Hashmap
        // One example of the map is: {"Train":["station1 platform 1 time-time", "station2 platform2 time-time"}
        findPathToSchool.parseTrips(line);

        // filter one possible trip with the user preference
        Records records = findPathToSchool.filterTrip();

        return records;

//        mStationText.setText(trip_information);
    }

//    public static void main (String [] args) {
//        // here is an example
//        double [] currentLocation = new double [] {-33.9661649,151.1030075};
//        getTripInformation(currentLocation,"Train");
//    }
}