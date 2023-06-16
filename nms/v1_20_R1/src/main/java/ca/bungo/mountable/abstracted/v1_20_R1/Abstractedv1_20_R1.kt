package ca.bungo.mountable.abstracted.v1_19_R3

import ca.bungo.mountable.abstracted.AbstractedHandler
import ca.bungo.mountable.abstracted.AbstractedLink
import net.minecraft.world.entity.PathfinderMob
import net.minecraft.world.entity.ai.goal.Goal
import net.minecraft.world.entity.player.Player
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.craftbukkit.v1_20_R1.entity.CraftEntity
import org.bukkit.entity.Entity
import org.bukkit.inventory.ItemStack
import org.bukkit.util.Vector

class Abstractedv1_20_R1(private val link: AbstractedLink) : AbstractedHandler(link) {


    override fun addMountPathfinder(entity: Entity?, player: org.bukkit.entity.Player, heldItem: Material, speed: Double) {
        val nmsEntity: net.minecraft.world.entity.Entity = (entity as CraftEntity).handle
        if(nmsEntity !is PathfinderMob) return

        nmsEntity.goalSelector.addGoal(0, MountableGoal(nmsEntity, link, heldItem, speed))
    }


    class MountableGoal(mob: PathfinderMob, private val link: AbstractedLink,
                        private val heldItem: Material, private val speed: Double) : Goal() {
        private val pathfinder = mob
        private var player: org.bukkit.entity.Player? = null


        override fun canUse(): Boolean {
            if(pathfinder.getPassengers().size != 0){
                if(pathfinder.getPassengers()[0] is Player){
                    val player = pathfinder.getPassengers()[0].bukkitEntity
                    if(player !is org.bukkit.entity.Player)
                        return false
                    this.player = player
                    if(player.inventory.itemInMainHand.type == heldItem || player.inventory.itemInOffHand.type == heldItem)
                        return true
                    return false
                }
                return false
            }
            return false
        }

        private fun getVector(): Vector{
            if(pathfinder.getPassengers().size != 0) {
                if (pathfinder.getPassengers()[0] is Player) {
                    val player = pathfinder.getPassengers()[0].bukkitEntity
                    if (player is org.bukkit.entity.Player){
                        val bukkitPlayer:org.bukkit.entity.Player = player as org.bukkit.entity.Player
                        return bukkitPlayer.location.direction
                    }

                }
            }
            return Vector(0,0,0)
        }

        override fun start() {
            val vector = getVector()
            this.pathfinder.navigation.moveTo(vector.x, vector.y, vector.z, speed)
        }

        override fun canContinueToUse(): Boolean {
            return !this.pathfinder.navigation.isDone
        }

        override fun tick() {
            val vector = getVector()
            if(player == null)
                return
            val location = player!!.location.add(vector.multiply(2))
            this.pathfinder.navigation.moveTo(location.x, location.y, location.z, speed)
        }

    }


}