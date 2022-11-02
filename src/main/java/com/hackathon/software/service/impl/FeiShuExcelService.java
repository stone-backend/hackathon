package com.hackathon.software.service.impl;

import com.hackathon.software.service.ExcelService;
import com.lark.oapi.Client;
import com.lark.oapi.service.sheets.v3.model.GetSpreadsheetReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Deprecated
public class FeiShuExcelService extends ExcelService {

    private final Client feiShuClient;

    public void read() throws Exception {
        String sheetToken = "shtcnV6MKsIh63CkQtbYkrRmItf";
        feiShuClient.sheets().spreadsheet().get(GetSpreadsheetReq.newBuilder().spreadsheetToken(sheetToken).build());
    }
}
