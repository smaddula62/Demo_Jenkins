/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mss.ediscv.logisticsshipment;

import com.mss.ediscv.util.ServiceLocatorException;
import java.util.ArrayList;
/**
 *
 * @author miracle
 */
public interface LtShipmentService {
    public ArrayList<LtShipmentBean> getLtResponseList(LogisticsShipmentAction logisticsShipmentAction)throws ServiceLocatorException;
}
