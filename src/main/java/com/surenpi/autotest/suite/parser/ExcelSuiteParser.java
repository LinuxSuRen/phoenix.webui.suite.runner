/*
 * Copyright 2002-2007 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.surenpi.autotest.suite.parser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.surenpi.autotest.suite.runner.Suite;
import com.surenpi.autotest.suite.runner.SuiteAction;
import com.surenpi.autotest.suite.runner.SuitePage;

/**
 * Excel文件格式的测试套件解析器
 * @author <a href="http://surenpi.com">suren</a>
 * @since 1.1.0
 */
public class ExcelSuiteParser implements SuiteParser
{
    private Suite suite;
    private Workbook workbook;
    private int maxRows = 100;

    @Override
    public Suite parse(InputStream suiteInputStream) throws Exception
    {
        if(POIFSFileSystem.hasPOIFSHeader(suiteInputStream))
        {
            workbook = new HSSFWorkbook(suiteInputStream);
        }
        else if(POIXMLDocument.hasOOXMLHeader(suiteInputStream))
        {
            workbook = new XSSFWorkbook(OPCPackage.open(suiteInputStream));
        }
        else
        {
            throw new IllegalArgumentException("Unknow format excel file.");
        }
        
        suite = new Suite();
        suite.setRows("1");
        List<SuitePage> pageList = new ArrayList<SuitePage>();
        suite.setPageList(pageList);

        sheetsParse(pageList);
        
        return suite;
    }

    /**
     * @param pageList
     */
    private void sheetsParse(List<SuitePage> pageList)
    {
        int total = workbook.getNumberOfSheets();
        
        for(int index = 0; index < total; index++)
        {
            Sheet sheet = workbook.getSheetAt(index);
            
            SuitePage suitePage = new SuitePage();
            if(sheetParse(sheet, suitePage))
            {
                suitePage.setRepeat(1);
                pageList.add(suitePage);
            }
        }
    }

    /**
     * @param sheet
     * @param suitePage
     * @return
     */
    private boolean sheetParse(Sheet sheet, SuitePage suitePage)
    {
        if(sheet.getSheetName().equals("SuiteConfig"))
        {
            for(int i = 0; i < maxRows; i++)
            {
                Row row = sheet.getRow(i);
                if(row == null)
                {
                    break;
                }
                
                Cell keyCell = row.getCell(0);
                Cell valCell = row.getCell(1);
                if(keyCell == null || valCell == null)
                {
                    continue;
                }
                
                String keyCellVal = keyCell.getStringCellValue();
                String valCellVal = valCell.getStringCellValue();
                if(keyCellVal.equals("PageConfig"))
                {
                    suite.setXmlConfPath(valCellVal);
                }
                else if(keyCellVal.equals("PagePackage"))
                {
                    suite.setPagePackage(valCellVal);
                }
                else if(keyCellVal.equals("AfterSleep"))
                {
                    try
                    {
                        suite.setAfterSleep(Long.parseLong(valCellVal));
                    }
                    catch(NumberFormatException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
            
            return false;
        }

        suitePage.setPage(sheet.getSheetName());
        suitePage.setActionList(new ArrayList<SuiteAction>());
        
        for(int i = 0; i < maxRows; i++)
        {
            Row row = sheet.getRow(i);
            if(row == null)
            {
                break;
            }
            
            SuiteAction suiteAction = new SuiteAction();
            suiteAction.setRepeat(1);
            rowParse(row, suiteAction);
            suitePage.getActionList().add(suiteAction);
        }
        
        return true;
    }

    /**
     * @param row
     * @param suiteAction
     */
    private void rowParse(Row row, SuiteAction suiteAction)
    {
        Cell nameCell = row.getCell(0);
        Cell actionCell = row.getCell(1);
        
        if(nameCell == null || actionCell == null)
        {
            return;
        }
        
        suiteAction.setField(nameCell.getStringCellValue());
        suiteAction.setName(actionCell.getStringCellValue());
    }

}
