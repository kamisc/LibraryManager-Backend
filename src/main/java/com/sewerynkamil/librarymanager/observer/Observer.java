package com.sewerynkamil.librarymanager.observer;

import com.sewerynkamil.librarymanager.domain.Rent;

/**
 * Author Kamil Seweryn
 */

public interface Observer {
    void update(Rent rent);
}
