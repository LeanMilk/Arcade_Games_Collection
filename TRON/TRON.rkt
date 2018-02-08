;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-intermediate-lambda-reader.ss" "lang")((modname TRON) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f ())))
(require 2htdp/image)
(require 2htdp/universe)


; A World is Player1 and Player2 
;(make-world player1 player2)
(define-struct world (p1 p2))


; A Player1 is: 
; -- (make-player Direction Bike Track)
(define-struct player (direction color bike track))



; A Direction is one of:
; -- "north"
; -- "east"
; -- "south"
; -- "west"
 
; A Bike is:
; -- (make-point Number Number)
(define-struct point (x y))
 
; A Track is one of:
; -- empty
; -- (cons Point Track)
 

(define WIDTH 10)


(define p1 (make-player "north" "blue" (make-point 200 200) empty))


(define p2 (make-player "south" "orange" (make-point 150 120) (list (make-point 150 118)
                                                           (make-point 150 116)
                                                           (make-point 150 114)
                                                           (make-point 150 112)
                                                           (make-point 150 110)
                                                           (make-point 150 108)
                                                           (make-point 150 106)
                                                           (make-point 150 104)
                                                           (make-point 150 102)
                                                           (make-point 150 100))))

(define w1 (make-world p1 p2))


; World -> Image
; renders the current world. 
(define (render-world world) 
  (render-player (world-p1 world)
                 (render-player (world-p2 world)
                                (empty-scene 400 400))))



; Player -> Image 
; renders the Player 
(define (render-player player img) 
  (render-bike player
               (render-track player
                             img)))


(define RADIUS (/ WIDTH 2))

; Bike Image -> Image 
;renders the Bike on some backgroung Image. 
(define (render-bike player img) 
  (place-image (circle RADIUS "solid" (player-color player)) 
               (point-x (player-bike player)) (point-y (player-bike player)) 
               img))

; Points Image -> Image 
; renders the track on some background image. 
(define (render-track player img) 
  (cond 
    [(empty? (player-track player)) img]
    [else (place-image (square 2 "solid" (player-color player))
                       (point-x (first (player-track player)))
                       (point-y (first (player-track player)))
                       (render-track (make-player (player-direction player) 
                                                  (player-color player) 
                                                  (player-bike player) 
                                                  (rest (player-track player))) img))]))


; World -> World
; updates world every tick
(define (update-world world)
  (make-world (move-player (world-p1 world))
              (move-player (world-p2 world))))


; Player -> Player
; 
(define (move-player player) 
  (make-player (player-direction player)
               (player-color player)
               (move-bike (player-direction player) (player-bike player))
               (leave-track (player-bike player) (player-track player))))


; Point -> Point
; generates a new segment, at the new location of the Bike
(define (move-bike direction bike) 
  (cond ((string=? direction "north") (make-point (point-x bike) (- (point-y bike) 2))) 
        ((string=? direction "south") (make-point (point-x bike) (+ (point-y bike) 2))) 
        ((string=? direction "west" ) (make-point (- (point-x bike) 2) (point-y bike))) 
        ((string=? direction "east" ) (make-point (+ (point-x bike) 2) (point-y bike)))))


; Bike Track -> Track
; moves the Bike and leaves new Track 
(define (leave-track bike track) 
  (cons bike track))


; World Key -> World
; modifies the world depending on what key is pressed
(define (change-world-accordingly world key)
  (cond ((string=? key "up"   ) (point-player1 "north" world))
        ((string=? key "down" ) (point-player1 "south" world))
        ((string=? key "left" ) (point-player1 "west"  world))
        ((string=? key "right") (point-player1 "east"  world))
        ((string=? key "w"    ) (point-player2 "north" world))
        ((string=? key "s"    ) (point-player2 "south" world))
        ((string=? key "a"    ) (point-player2 "west"  world))
        ((string=? key "d"    ) (point-player2 "east"  world))
        (else world)))

; World -> World
; changes the direction of the Bike in the world
(define (point-player1 direction world) 
  (make-world (make-player direction 
                           (player-color (world-p1 world))
                           (player-bike (world-p1 world)) 
                           (player-track (world-p1 world))) 
              (world-p2 world)))

(define (point-player2 direction world) 
  (make-world (world-p1 world)
              (make-player direction 
                           (player-color (world-p2 world))
                           (player-bike (world-p2 world)) 
                           (player-track (world-p2 world))) 
              ))
  

(define (main initial)
  (big-bang initial
            (on-tick update-world)
            (to-draw render-world)
            (on-key change-world-accordingly)
;            (stop-when game-ends and-say-goodbye)
            ))