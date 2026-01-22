package jpp.battleship.game;

import jpp.battleship.logic.strategy.TargetStrategy;

public enum Difficulty {
    EASY,
    MEDIUM,
            HARD ;

//    EASY	RandomStrategy
//    MEDIUM	RandomAndHuntStrategy
//    HARD	ProbabilityAndHuntStrategy

    public TargetStrategy strategy(){
      if(this==EASY){
          return TargetStrategy.RandomStrategy();
      }
      else if(this==MEDIUM){
          return TargetStrategy.RandomAndHuntStrategy();
      }
      else{
          return TargetStrategy.ProbabilityAndHuntStrategy();
      }


    }
}
