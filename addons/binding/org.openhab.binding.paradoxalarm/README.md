## Example things configuration
# Example things configuration
####For the ip150 bridge configuration arguments are as follows
**refresh** - value is in seconds. Defines the refresh interval when the binding polls from paradox system.<br>
**ip150Password** - pretty obvious. The password to your IP150 (not your panel PIN).<br>
**pcPassword** - The code 3012 setting. Default value is 0000 for Paradox<br>
**ipAddress** - pretty obvious. IP address of your IP150<br>
**port** - the port used for data communication. Default is 10000 for Paradox<br>
#### For the entities configuration arguments are:
**id** - the numeric ID of the zone/partition<br>
**refresh** - Interval on which the things poll from the cache (Probably would be wise to be implemented with something like a callback in the future)<br>
<code>paradoxalarm:paradoxCommunication:panel [refresh=5, panelType="EVO192", ip150Password="<YOUR IP150 PASSWORD>", pcPassword="0000", ipAddress="10.10.10.10", port=10000 ] { <br><br>
Thing panel panel [refresh=10]<br><br>
Thing partition partition1 [id=1, refresh=10] <br>
Thing paradoxalarm:partition:partition2 [id=2, refresh=10] <br>
Thing paradoxalarm:partition:partition3 [id=3, refresh=10] <br>
Thing paradoxalarm:partition:partition4 [id=4, refresh=10] <br><br>
Thing paradoxalarm:zone:bedroomBathMUC [id=20, refresh=10] <br>
}<br>
</code><br><br>
## Example items configuration

<code>
String sendCommand "Send command to IP150" {channel="paradoxalarm:ip150:communicator:command"}

String panelType "Paradox panel type: [%s]" <lock> (Security) { channel = "paradoxalarm:panel:ip150:panel:panelType" }<br>
String serialNumber "Paradox Serial number: [%s]" <lock> (Security) { channel = "paradoxalarm:panel:ip150:panel:serialNumber" }<br>
String hardwareVersion "Paradox HW version: [%s]" <lock> (Security) { channel = "paradoxalarm:panel:ip150:panel:hardwareVersion" }<br>
String applicationVersion "Paradox Application version: [%s]" <lock> (Security) { channel = "paradoxalarm:panel:ip150:panel:applicationVersion" }<br>
String bootloaderVersion "Paradox Bootloader version: [%s]" <lock> (Security) { channel = "paradoxalarm:panel:ip150:panel:bootloaderVersion" }<br>

String partition1Label "Partition1 label: [%s]" <lock> (Security) { channel = "paradoxalarm:partition:ip150:partition3:label" }<br>
String partition1State "Partition1 state: [%s]" <lock> (Security) { channel = "paradoxalarm:partition:ip150:partition3:state" }<br>
String partition1AdditionalStates "Partition1 additional states: [%s]" <lock> (Security) { channel = "paradoxalarm:partition:ip150:partition3:addidionalStates" }<br>

String partition2Label "Partition2 label: [%s]" <lock> (Security) { channel = "paradoxalarm:partition:ip150:partition2:label" }<br>
String partition2State "Partition2 state: [%s]" <lock> (Security) { channel = "paradoxalarm:partition:ip150:partition2:state" }<br>
String partition2AdditionalStates "Partition2 additional state: [%s]" <lock> (Security) { channel = "paradoxalarm:partition:ip150:partition2:addidionalStates" }<br>

String zoneBedRoomLabel "Zone label: [%s]" <lock> (Security) { channel = "paradoxalarm:zone:ip150:bedroomBathMUC:label" }<br>
Contact zoneBedRoomIsOpened "Zone opened: [%s]" <lock> (Security) { channel = "paradoxalarm:zone:ip150:bedroomBathMUC:isOpened" }<br>
Contact zoneBedRoomIsTampered "Zone tampered: [%s]" <lock> (Security) { channel = "paradoxalarm:zone:ip150:bedroomBathMUC:isTampered" }<br>
Contact zoneBedRoomHasLowBattery "Zone low battery: [%s]" <lock> (Security) { channel = "paradoxalarm:zone:ip150:bedroomBathMUC:hasLowBattery" }<br>
<br>