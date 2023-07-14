package com.telros.telrostestcase.view;

import com.telros.telrostestcase.model.User;
import com.telros.telrostestcase.repository.UserRepo;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route("/user")
public class MainView extends VerticalLayout {
    private UserRepo userRepo;
    private Grid<User> userGrid = new Grid<>(User.class);

@Autowired
    public MainView(UserRepo userRepo) {
        this.userRepo = userRepo;
        add(userGrid);
        userGrid.setItems(userRepo.findAll());
    }


}
