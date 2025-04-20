package com.app.util;

import com.app.controller.ElectionController;
import com.app.dao.ElectionDAO;
import com.app.model.ElectionModel;
import com.app.view.ElectionView;

public class ElectionAppMain {

    public static void main(String[] args) {
         // Initialize the View, Model, and Controller
        ElectionView view = new ElectionView();
        ElectionModel model = new ElectionModel();
        ElectionDAO dao = new ElectionDAO();
        ElectionController controller = new ElectionController(view, model, dao);
        // Run the Election system
        controller.run();  // This method will start the flow of the application
        
    }
}
