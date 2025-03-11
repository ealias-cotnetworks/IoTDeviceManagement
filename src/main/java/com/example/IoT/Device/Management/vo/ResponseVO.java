package com.example.IoT.Device.Management.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ResponseVO<T> {
  @JsonProperty("status")
  private int status;

  @JsonProperty("message")
  private String message;

  @JsonProperty("data")
  private List<T> data;

  @JsonProperty("total_count")
  private long totalCount;


  @JsonProperty("error")
  private ErrorVO error;

  public ResponseVO() {
    super();
    this.data = new ArrayList<>();
    this.status = 200;
    this.message = "success";
  }

  public void addPaginatedDataList(List<T> data, long totalCount) {
    if (data != null && !data.isEmpty()) {
      this.data.addAll(data);
      this.totalCount = totalCount;
    }
  }

  public void addData(T data) {
    if (data != null) {
      this.data.add(data);
    }
  }
}