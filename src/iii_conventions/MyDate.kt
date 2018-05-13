package iii_conventions

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int)

operator fun MyDate.compareTo(other: MyDate): Int {
    if(year != other.year) return year - other.year
    if(month != other.month) return month - other.month
    return dayOfMonth - other.dayOfMonth
}

operator fun MyDate.rangeTo(other: MyDate): DateRange = DateRange(this, other)

operator fun MyDate.plus(timeInterval: TimeInterval): MyDate {
    return addTimeIntervals(timeInterval, 1)
}

operator fun MyDate.plus(timeIntervals: RepeatedTimeInterval) : MyDate {
    return addTimeIntervals(timeIntervals.timeInterval, timeIntervals.number)
}

class DateRange(val start: MyDate, val endInclusive: MyDate): Iterable<MyDate> {
    override fun iterator(): Iterator<MyDate> {
        return DateIterator(this)
    }
}

operator fun DateRange.contains(other: MyDate): Boolean {
    return start <= other && other <= endInclusive
}

class DateIterator(val dateRange: DateRange) : Iterator<MyDate> {
    private var current: MyDate = dateRange.start
    override fun next(): MyDate {
        val result = this.current
        this.current = this.current.nextDay()
        return result
    }
    override fun hasNext(): Boolean = this.current <= dateRange.endInclusive
}

class RepeatedTimeInterval(val timeInterval: TimeInterval, val number: Int)

operator fun TimeInterval.times(number: Int): RepeatedTimeInterval {
    return RepeatedTimeInterval(this, number)
}

enum class TimeInterval {
    DAY,
    WEEK,
    YEAR
}