package parking

data class Car(val registration: String = "", val color: String = "")

data class ParkingSpot(var occupied: Boolean = false, var car: Car? = null)

class ParkingLot(val parkingSpots: MutableList<ParkingSpot>) {
    fun park(car: Car) {
        for (spot in parkingSpots) {
            if (!spot.occupied) {
                spot.occupied = true
                spot.car = car
                println("${car.color.lowercase().replaceFirstChar {it.uppercase()}} car parked in spot ${parkingSpots.indexOf(spot) + 1}.")
                return
            }
        }
        println("Sorry, the parking lot is full.")
    }

    fun leave(number: Int) {
        val index = number - 1
        if (parkingSpots[index].occupied) {
            parkingSpots[index].occupied = false
            parkingSpots[index].car = null
            println("Spot $number is free.")
        } else {
            println("There is no car in spot $number.")
        }
    }

    fun status() {
        var count = 0
        for (spot in parkingSpots) {
            if (spot.occupied) {
                println("${parkingSpots.indexOf(spot) + 1} ${spot.car!!.registration} ${spot.car!!.color}")
                count++
            }
        }

        if (count == 0) {
            println("Parking lot is empty.")
        }
    }

    fun getRegistrationByColor(color: String) {
        val regs = mutableListOf<String>()

        for (spot in parkingSpots) {
            if (spot.occupied) {
                if (spot.car!!.color.equals(color)) regs.add(spot.car!!.registration)
            }
        }

        if (regs.size > 0) {
            println(regs.joinToString(", "))
        } else {
            println("No cars with color $color were found.")
        }
    }

    fun getSpotByColor(color: String) {
        val spots = mutableListOf<Int>()

        for (spot in parkingSpots) {
            if (spot.occupied) {
                if (spot.car!!.color.equals(color)) spots.add(parkingSpots.indexOf(spot) + 1)
            }
        }

        if (spots.size > 0) {
            println(spots.joinToString(", "))
        } else {
            println("No cars with color $color were found.")
        }
    }

    fun getSpotByRegistration(registration: String) {
        for (spot in parkingSpots) {
            if (spot.occupied) {
                if (spot.car!!.registration.equals(registration)) {
                    println(parkingSpots.indexOf(spot) + 1)
                    return
                }
            }
        }
        println("No cars with registration number $registration were found.")
    }
}

fun createParkingLot(count: Int): ParkingLot {
    val parkingLot = ParkingLot(MutableList(count) { ParkingSpot() })
    println("Created a parking lot with $count spots.")
    return parkingLot
}

fun main() {
    var input = readln().split(" ")

    while (input[0] != "create" && input[0] != "exit") {
        println("Sorry, a parking lot has not been created.")
        input = readln().split(" ")
    }

    if (input[0] == "exit") return

    var parkingLot = createParkingLot(input[1].toInt())

    while (true) {
        input = readln().split(" ")

        when (input[0]) {
            "exit" -> return
            "create" -> parkingLot = createParkingLot(input[1].toInt())
            "park" -> parkingLot.park(Car(input[1].uppercase(), input[2].uppercase()))
            "leave" -> parkingLot.leave(input[1].toInt())
            "status" -> parkingLot.status()
            "reg_by_color" -> parkingLot.getRegistrationByColor(input[1].uppercase())
            "spot_by_color" -> parkingLot.getSpotByColor(input[1].uppercase())
            "spot_by_reg" -> parkingLot.getSpotByRegistration(input[1].uppercase())
        }
    }
}
