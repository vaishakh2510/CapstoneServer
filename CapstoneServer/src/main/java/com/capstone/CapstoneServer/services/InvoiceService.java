package com.capstone.CapstoneServer.services;


import com.capstone.CapstoneServer.dto.InvoiceDto;
import com.capstone.CapstoneServer.exception.InvoiceNotFoundException;
import com.capstone.CapstoneServer.exception.UserNotFoundException;

import java.util.List;

public interface InvoiceService {

    Boolean addInvoice(InvoiceDto invoiceDto) throws UserNotFoundException;

    List<InvoiceDto> getInvoicesByUserId(int userId) throws UserNotFoundException;

    Boolean updateInvoice(int id, InvoiceDto invoiceDto) throws InvoiceNotFoundException;

    int deleteInvoice(int id) throws InvoiceNotFoundException;

    InvoiceDto getInvoiceByInvoiceId(int invoiceId) throws InvoiceNotFoundException;
}
