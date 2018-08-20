package cn.org.upthink.common.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author：Jaince
 * @Date：2018/08/09
 * @Description：地区编码工具类
 */
public class AreaUtil {

    private static String areaJson = "";

    private static Map<String, String> areaMap = new HashMap<>();

    /**
     * 根据省市区编码获取省市区地址名（联动获取）
     * 注意：获取后一个地址名，前一个code必须传
     * @param provinceCode
     * @param cityCode
     * @param districtCode
     * @return
     */
    public static String getAddressNameByCodes(String provinceCode, String cityCode, String districtCode) {
        JSONObject provinceJsonObject = getProvinceByCode(provinceCode);
        String areaString = "";
        if (null!= provinceJsonObject){
            areaString += provinceJsonObject.getString("province");
            if (StringUtils.isNotBlank(cityCode)) {
                JSONArray cityObjectArray = provinceJsonObject.getJSONArray("citys");
                if (cityObjectArray != null && !cityObjectArray.isEmpty()) {
                    for (Object cityObject : cityObjectArray) {
                        JSONObject cityJsonObject = (JSONObject) cityObject;
                        if (cityJsonObject.getString("code").equals(cityCode)) {
                            areaString += cityJsonObject.getString("city");
                            JSONArray districtArray = cityJsonObject.getJSONArray("district");
                            if (districtArray != null && !districtArray.isEmpty()) {
                                for (Object districtObject : districtArray) {
                                    JSONObject districtJsonObject = (JSONObject) districtObject;
                                    if (StringUtils.isNotBlank(districtCode)) {
                                        if (districtJsonObject.getString("code").equals(districtCode)) {
                                            areaString += districtJsonObject.getString("district");
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return areaString;
    }

    /**
     * 根据code获取地址的名称
     * @param code
     * @return
     */
    public static String getAreaByCode(String code){
        String addressName = "";
        if (StringUtils.isBlank(code)){
            return addressName;
        }
        Map<String, String> map = initAreaMap();
        addressName = map.get(code);
        return addressName;
    }

    /**
     *
     * @param provinceCode
     * @return
     */
    public static JSONObject getProvinceByCode(String provinceCode) {
        //todo 是否需联动？
        if (StringUtils.isBlank(provinceCode)){
            return null;
        }
        if (StringUtils.isBlank(areaJson)){
            areaJson = XMLToJSON();
        }
        JSONArray areaJsonArray = JSONArray.parseArray(areaJson);
        if (areaJsonArray != null && !areaJsonArray.isEmpty()) {
            for (Object provinceObject : areaJsonArray) {
                JSONObject provinceJsonObject = (JSONObject) provinceObject;
                if (provinceJsonObject.getString("code").equals(provinceCode)) {
                    return provinceJsonObject;
                }
            }
        }
        return null;
    }

    /**
     *
     * @param provinceCode
     * @param cityCode
     * @return
     */
    public static JSONObject getProvinceAndCityByCode(String provinceCode, String cityCode) {
        JSONObject provinceJsonObject = getProvinceByCode(provinceCode);
        if (null != provinceJsonObject) {
            JSONArray areaCityArray = provinceJsonObject.getJSONArray("citys");
            if (areaCityArray != null && !areaCityArray.isEmpty()) {
                for (Object cityObject : areaCityArray) {
                    JSONObject cityJsonObject = (JSONObject) cityObject;
                    if (cityJsonObject.getString("code").equals(cityCode)) {
                        return cityJsonObject;
                    }
                }
            }
        }
        return null;
    }

    /**
     *
     * @param provinceCode
     * @param cityCode
     * @param districtCode
     * @return
     */
    public static JSONObject getProvinceAndCityAndDistrictByCode(String provinceCode, String cityCode, String districtCode) {
        JSONObject cityJsonObject = getProvinceAndCityByCode(provinceCode, cityCode);
        if (null != cityJsonObject) {
            JSONArray areaDistrictArray = cityJsonObject.getJSONArray("district");
            if (areaDistrictArray != null && !areaDistrictArray.isEmpty()) {
                for (Object areaDistrictObject : areaDistrictArray) {
                    JSONObject districtJsonObject = (JSONObject) areaDistrictObject;
                    if (districtJsonObject.getString("code").equals(districtCode)) {
                        return districtJsonObject;
                    }
                }
            }
        }
        return null;
    }

    /**
     * 初始化地址Map
     * @return
     */
    public static Map<String, String> initAreaMap(){
        if (StringUtils.isBlank(areaJson)){
            areaJson = XMLToJSON();
        }
        if (areaMap!=null && !areaMap.isEmpty()){
            return areaMap;
        }
        JSONArray areaJsonArray = JSONArray.parseArray(areaJson);
        if (areaJsonArray != null && !areaJsonArray.isEmpty()) {
            for (Object provinceObject : areaJsonArray) {
                JSONObject provinceJsonObject = (JSONObject) provinceObject;
                String province = provinceJsonObject.getString("province");
                String codeProvince = provinceJsonObject.getString("code");
                areaMap.put(codeProvince, province);
                JSONArray cityObjectArray = provinceJsonObject.getJSONArray("citys");
                if (cityObjectArray != null && !cityObjectArray.isEmpty()) {
                    for (Object cityObject : cityObjectArray) {
                        JSONObject cityJsonObject = (JSONObject) cityObject;
                        String city = cityJsonObject.getString("city");
                        String codeCity = cityJsonObject.getString("code");
                        areaMap.put(codeCity, city);
                        JSONArray districtArray = cityJsonObject.getJSONArray("district");
                        if (districtArray != null && !districtArray.isEmpty()) {
                            for (Object districtObject : districtArray) {
                                JSONObject districtJsonObject = (JSONObject) districtObject;
                                String district = districtJsonObject.getString("district");
                                String codeDistrict = districtJsonObject.getString("code");
                                areaMap.put(codeDistrict, district);
                            }
                        }
                    }
                }
            }
        }
        return areaMap;
    }

    @SuppressWarnings("rawtypes")
    public static String XMLToJSON() {
        String json = "";
        InputStream is = AreaUtil.class.getClassLoader().getResourceAsStream("json/area.json");
        try {
            json = IOUtils.toString(is, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

}
