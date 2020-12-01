package com.sokolmeteo.sokol.http.dto;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Map;

@Getter
public class WRecordResponse extends ArrayList<Map<String, Object>> implements SokolListResponse {
}
