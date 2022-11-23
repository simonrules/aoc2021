open class Packet(version: Int, type: Int)

class Literal(version: Int, type: Int, value: Int) : Packet(version, type)

class Operator(version: Int, type: Int, subPackets: List<Packet>) : Packet(version, type)
