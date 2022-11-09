package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import contract.IOrderPerformer;
import contract.IBoulderDashController;
import contract.IModel;
import contract.IView;
import contract.UserOrder;
import entity.Permeability;
import entity.mobile.MobileElementsFactory;
import entity.motionless.MotionlessElementsFactory;

public final class Controller implements IBoulderDashController, IOrderPerformer {

    private static final int speed = 200;
    private IView view;
    private IModel model;
    private UserOrder stackOrder;
    private final int diamondGoal = 12;
    private boolean hasWon = false;
    private final Random rand = new Random();
    private int rdirection;
    private int gDirection;
    private boolean canChange = true;
    /**
     * le constructeur Controller
     * @param view
     * @param model
     */
    public Controller(final IView view, final IModel model) {
        this.setView(view);
        this.setModel(model);
        this.clearStackOrder();
    }
    @Override
    //méthode qui gère le deroulement du jeu.
    public final void play() {
        while (this.getModel().getMyPlayer().isAlive() == true && hasWon == false) {

            // --- Vitesse du jeu ---
            try {
                Thread.sleep(speed);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // --- joueur ---
            this.movePlayer();
            this.killPlayer();
            this.winPlayer();

            gDirection = gDirection + 1;
            rdirection = rand.nextInt(4);

            // --- Update Mobile Entities on Map
            for (int y = this.getModel().getMap().getHeight() - 1; y > 0; y--) {
                for (int x = this.getModel().getMap().getWidth() - 1; x > 0; x--) {

                    for (int g = 0; g < this.getModel().getMap().getHasChanged().size(); g = g + 2) {
                        if (this.getModel().getMap().getHasChanged().get(g) == x || this.getModel().getMap().getHasChanged().get(g + 1) == y) {
                            this.canChange = false;
                        }
                    }

                    if (canChange) {
                        // --- Rochers ---
                        this.gravityBoulder(x, y);
                        this.gravityDiagBoulder(x, y);

                        // --- Diamants ---
                        this.gravityDiamond(x, y);
                        this.gravityDiagDiamond(x, y);

                        // --- Monstres ---
                        this.killMonster(x, y);
                        this.moveGMonster(x, y);
                        this.moveRMonster(x, y);
                    }
                    this.canChange = true;
                }
            }

            // --- mise à jour de view ---
            this.getModel().getMap().getHasChanged().clear();
            this.canChange = true;
            this.getView().followMyPlayer();
            this.getView().updateView();
            this.getView().getBoardFrame().setTitle("BoulderDash - Diamond Counter: " + this.getModel().getMyPlayer().getDiamonds());
        }
    }


    /**
     * méthode permettant de faire bouger le joueur
     */
    public void movePlayer() {
        switch (this.getStackOrder()) {
            case UP:
                this.getModel().getMyPlayer().moveUp();

                break;
            case DOWN:
                this.getModel().getMyPlayer().moveDown();

                break;
            case RIGHT:
                this.getModel().getMyPlayer().moveRight();

                break;
            case LEFT:
                this.getModel().getMyPlayer().moveLeft();

                break;
            case NOP:
            default:
                this.getModel().getMyPlayer().doNothing();
                break;
        }
        this.clearStackOrder();
    }

    /**
     * méthode permettant de voir le processus par lequel , un joueur peut être tué.
     */
    public void killPlayer() {
        if ((this.getModel().getMap().getOnTheMapXY((this.getModel().getMyPlayer().getX()), ((this.getModel().getMyPlayer().getY()))).getPermeability() == Permeability.KILLABLE)) {
            this.getModel().getMyPlayer().die();
            this.getView().displayMessage("Vous avez échoué! Voulez-vous continuer ?");
        }
    }

    public void winPlayer() {
        if ((this.getModel().getMap().getOnTheMapXY((this.getModel().getMyPlayer().getX()), ((this.getModel().getMyPlayer().getY()))).getPermeability() == Permeability.EXIT) && this.getModel().getMyPlayer().getDiamonds() >= diamondGoal) {
            this.hasWon = true;
            this.getModel().getMyPlayer().win();
            this.getView().displayMessage("Vous avez gagné, Felicitation! Voulez-vous recommencer ?");
        }
    }


    /**
     * méthode qui gère la gravité du rocher.
     */
    public void gravityBoulder(int x, int y) {
        if (this.getModel().getMap().getOnTheMapXY(x, y).getPermeability() == Permeability.BOULDER && this.getModel().getMap().getOnTheMapXY(x, y + 1).getPermeability() == Permeability.WALKABLE) {
            if (x != this.getModel().getMyPlayer().getX() || y + 1 != this.getModel().getMyPlayer().getY()) {
                this.getModel().getMap().setOnTheMapXY(MotionlessElementsFactory.createBackground(), x, y);
                this.getModel().getMap().setOnTheMapXY(MobileElementsFactory.createRock(), x, y + 1);
            }
        }
    }

    /**
     * méthode gerant la gravité du rocher plus complexe avec une succesion des structures conditionelles.
     */
    public void gravityDiagBoulder(int x, int y) {
        if (this.getModel().getMap().getOnTheMapXY(x, y).getPermeability() == Permeability.BOULDER && this.getModel().getMap().getOnTheMapXY(x, y + 1).getPermeability() == Permeability.BOULDER) {
            if (this.getModel().getMap().getOnTheMapXY(x + 1, y + 1).getPermeability() == Permeability.WALKABLE && this.getModel().getMap().getOnTheMapXY(x + 1, y).getPermeability() == Permeability.WALKABLE) {
                if (x + 1 != this.getModel().getMyPlayer().getX() || y + 1 != this.getModel().getMyPlayer().getY()) {
                    if (x + 1 != this.getModel().getMyPlayer().getX() || y != this.getModel().getMyPlayer().getY()) {
                        this.getModel().getMap().setOnTheMapXY(MotionlessElementsFactory.createBackground(), x, y);
                        this.getModel().getMap().setOnTheMapXY(MobileElementsFactory.createRock(), x + 1, y);
                    }
                }
            }
        }
        if (this.getModel().getMap().getOnTheMapXY(x, y).getPermeability() == Permeability.BOULDER && this.getModel().getMap().getOnTheMapXY(x, y + 1).getPermeability() == Permeability.BOULDER) {
            if (this.getModel().getMap().getOnTheMapXY(x - 1, y + 1).getPermeability() == Permeability.WALKABLE && this.getModel().getMap().getOnTheMapXY(x - 1, y).getPermeability() == Permeability.WALKABLE) {
                if (x - 1 != this.getModel().getMyPlayer().getX() || y + 1 != this.getModel().getMyPlayer().getY()) {
                    if (x - 1 != this.getModel().getMyPlayer().getX() || y != this.getModel().getMyPlayer().getY()) {
                        this.getModel().getMap().setOnTheMapXY(MotionlessElementsFactory.createBackground(), x, y);
                        this.getModel().getMap().setOnTheMapXY(MobileElementsFactory.createRock(), x - 1, y);
                    }
                }
            }
        }
    }


    /**
     * méthode qui gère la gravité du diamant.
     */
    public void gravityDiamond(int x, int y) {
        if (this.getModel().getMap().getOnTheMapXY(x, y).getPermeability() == Permeability.DIAMOND && this.getModel().getMap().getOnTheMapXY(x, y + 1).getPermeability() == Permeability.WALKABLE) {
            if (x != this.getModel().getMyPlayer().getX() || y + 1 != this.getModel().getMyPlayer().getY()) {
                this.getModel().getMap().setOnTheMapXY(MotionlessElementsFactory.createBackground(), x, y);
                this.getModel().getMap().setOnTheMapXY(MobileElementsFactory.createDiamond(), x, y + 1);
            } else if (x == this.getModel().getMyPlayer().getX() && y + 1 == this.getModel().getMyPlayer().getY()) {
                this.getModel().getMap().setOnTheMapXY(MotionlessElementsFactory.createBackground(), x, y);
                this.getModel().getMap().setOnTheMapXY(MobileElementsFactory.createDiamond(), x, y + 1);
            }
        }
    }

    /**
     * méthode qui gère la gravité du diamant plus complexe avec des structures conditionelles
     */
    public void gravityDiagDiamond(int x, int y) {
        if (this.getModel().getMap().getOnTheMapXY(x, y).getPermeability() == Permeability.DIAMOND && this.getModel().getMap().getOnTheMapXY(x, y + 1).getPermeability() == Permeability.DIAMOND) {
            if (this.getModel().getMap().getOnTheMapXY(x + 1, y + 1).getPermeability() == Permeability.WALKABLE && this.getModel().getMap().getOnTheMapXY(x + 1, y).getPermeability() == Permeability.WALKABLE) {
                this.getModel().getMap().setOnTheMapXY(MotionlessElementsFactory.createBackground(), x, y);
                this.getModel().getMap().setOnTheMapXY(MobileElementsFactory.createDiamond(), x + 1, y);
            }
        }
        if (this.getModel().getMap().getOnTheMapXY(x, y).getPermeability() == Permeability.DIAMOND && this.getModel().getMap().getOnTheMapXY(x, y + 1).getPermeability() == Permeability.DIAMOND) {
            if (this.getModel().getMap().getOnTheMapXY(x - 1, y + 1).getPermeability() == Permeability.WALKABLE && this.getModel().getMap().getOnTheMapXY(x - 1, y).getPermeability() == Permeability.WALKABLE) {
                this.getModel().getMap().setOnTheMapXY(MotionlessElementsFactory.createBackground(), x, y);
                this.getModel().getMap().setOnTheMapXY(MobileElementsFactory.createDiamond(), x - 1, y);
            }
        }
    }


    /**
     * méthode permettant de tuer le monstre(l'ennemi)
     */
    public void killMonster(int x, int y) {
        if (this.getModel().getMap().getOnTheMapXY(x, y).getPermeability() == Permeability.KILLABLE && this.getModel().getMap().getOnTheMapXY(x, y - 1).getPermeability() == Permeability.BOULDER || this.getModel().getMap().getOnTheMapXY(x, y).getPermeability() == Permeability.KILLABLE && this.getModel().getMap().getOnTheMapXY(x, y - 1)
                .getPermeability() == Permeability.DIAMOND) {
            this.getModel().getMap().setOnTheMapXY(MobileElementsFactory.createDiamond(), x, y);

            for (int i = x - 1; i < x + 2; i++) {
                for (int j = y - 1; j < y + 2; j++) {
                    if (getModel().getMap().getOnTheMapXY(i, j).getPermeability() == Permeability.WALKABLE) {
                        this.getModel().getMap().setOnTheMapXY(MobileElementsFactory.createDiamond(), i, j);
                    }
                }
            }
        }
    }

    /**
     * méthode permettant de bouger le monstre vert.
     */
    public void moveGMonster(int x, int y) {
        if (gDirection <= 10) {
            MGMoveRight(x, y);
        } else if (gDirection <= 20) {
            MGMoveLeft(x, y);
        } else {
            gDirection = 0;
        }

    }

    /**
     * méthode permettant au montre de se déplacer à droite.
     */
    public void MGMoveRight(int x, int y) {
        if (this.getModel().getMap().getOnTheMapXY(x, y).getSprite().getConsoleImage() == 'G' && this.getModel().getMap().getOnTheMapXY(x + 1, y).getPermeability() == Permeability.WALKABLE) {
            this.getModel().getMap().setOnTheMapXY(MotionlessElementsFactory.createBackground(), x, y);
            this.getModel().getMap().setOnTheMapXY(MobileElementsFactory.createMonsterG(), x + 1, y);
        }
    }

    /**
     * méthode permettant au montre vert de bouger à gauche
     */
    public void MGMoveLeft(int x, int y) {
        if (this.getModel().getMap().getOnTheMapXY(x, y).getSprite().getConsoleImage() == 'G' && this.getModel().getMap().getOnTheMapXY(x - 1, y).getPermeability() == Permeability.WALKABLE) {
            this.getModel().getMap().setOnTheMapXY(MotionlessElementsFactory.createBackground(), x, y);
            this.getModel().getMap().setOnTheMapXY(MobileElementsFactory.createMonsterG(), x - 1, y);
        }
    }

    /**
     *méthode permettant au monstre rouge de se déplacer
     *
     * @throws InterruptedException
     */
    public void moveRMonster(int x, int y) {
        switch (rdirection) {
            case 1:
                MRMoveRight(x, y);
                break;
            case 0:
                MRMoveLeft(x, y);
                break;
            case 2:
                MRMoveUp(x, y);
                break;
            case 3:
                MRMoveDown(x, y);
                break;

            default:
                break;
        }
    }

    /**
     * méthode permettant au montre rouge de bouger à droite
     */
    public void MRMoveRight(int x, int y) {
        if (this.getModel().getMap().getOnTheMapXY(x, y).getSprite().getConsoleImage() == 'R' && this.getModel().getMap().getOnTheMapXY(x + 1, y).getPermeability() == Permeability.WALKABLE) {
            this.getModel().getMap().setOnTheMapXY(MotionlessElementsFactory.createBackground(), x, y);
            this.getModel().getMap().setOnTheMapXY(MobileElementsFactory.createMonsterR(), x + 1, y);
        }
    }

    /**
     * méthode permettant Monstre rouge de bouger à gauche.
     */
    public void MRMoveLeft(int x, int y) {
        if (this.getModel().getMap().getOnTheMapXY(x, y).getSprite().getConsoleImage() == 'R' && this.getModel().getMap().getOnTheMapXY(x - 1, y).getPermeability() == Permeability.WALKABLE) {
            this.getModel().getMap().setOnTheMapXY(MotionlessElementsFactory.createBackground(), x, y);
            this.getModel().getMap().setOnTheMapXY(MobileElementsFactory.createMonsterR(), x - 1, y);
        }
    }

    /**
     * méthode permettant au monstre rouge de se déplacer au dessus.
     */
    public void MRMoveUp(int x, int y) {
        if (this.getModel().getMap().getOnTheMapXY(x, y).getSprite().getConsoleImage() == 'R' && this.getModel().getMap().getOnTheMapXY(x, y - 1).getPermeability() == Permeability.WALKABLE) {
            this.getModel().getMap().setOnTheMapXY(MotionlessElementsFactory.createBackground(), x, y);
            this.getModel().getMap().setOnTheMapXY(MobileElementsFactory.createMonsterR(), x, y - 1);
        }
    }

    /**
     * méthode permettant au monstre de se déplacer en dessous.
     */
    public void MRMoveDown(int x, int y) {
        if (this.getModel().getMap().getOnTheMapXY(x, y).getSprite().getConsoleImage() == 'R' && this.getModel().getMap().getOnTheMapXY(x, y + 1).getPermeability() == Permeability.WALKABLE) {
            this.getModel().getMap().setOnTheMapXY(MotionlessElementsFactory.createBackground(), x, y);
            this.getModel().getMap().setOnTheMapXY(MobileElementsFactory.createMonsterR(), x, y + 1);
        }
    }


    /**
     * @return this.view
     */
    private IView getView() {
        return this.view;
    }

    /**
     * @param view
     */
    private void setView(final IView view) {
        this.view = view;
    }

    /**
     * @return this.model
     */
    private IModel getModel() {
        return this.model;
    }

    /**
     * @param model
     */
    private void setModel(final IModel model) {
        this.model = model;
    }

    /**
     * @return this.stackOrder
     */
    private UserOrder getStackOrder() {
        return this.stackOrder;
    }

    /**
     * @param stackOrder
     */
    private void setStackOrder(final UserOrder stackOrder) {
        this.stackOrder = stackOrder;
    }

    /**
     * clearStackOrder method
     */
    private void clearStackOrder() {
        this.stackOrder = UserOrder.NOP;
    }

    /**
     *  methode getOrderPerformer 
     */
    @Override
    public IOrderPerformer getOrderPerformer() {
        return this;
    }

    /**
     * methode orderPerform 
     */
    @Override
    public final void orderPerform(final UserOrder userOrder) throws IOException {
        this.setStackOrder(userOrder);
    }

}