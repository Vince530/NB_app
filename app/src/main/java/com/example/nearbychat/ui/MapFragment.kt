package com.example.nearbychat.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.amap.api.maps.MapView
import com.amap.api.maps.AMap
import com.example.nearbychat.R

class MapFragment : Fragment(R.layout.fragment_map) {
    private lateinit var mapView: MapView
    private lateinit var aMap: AMap

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_map, container, false)
        mapView = view.findViewById(R.id.mapView)  // 绑定 XML 里的地图组件
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapView.onCreate(savedInstanceState)  // 初始化地图
        aMap = mapView.map  // 获取地图控制对象

        // 启用定位蓝点
        aMap.isMyLocationEnabled = true
    }

    override fun onResume() { super.onResume(); mapView.onResume() }
    override fun onPause() { super.onPause(); mapView.onPause() }
    override fun onDestroy() { super.onDestroy(); mapView.onDestroy() }
}
