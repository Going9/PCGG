from django.db import models


class PeripheralReview(models.Model):
    rating = models.IntegerField()
    created_at = models.DateTimeField(auto_now_add=True)
    peripheral_id = models.IntegerField()
    user_id = models.IntegerField()
    type = models.CharField(max_length=50)
    review = models.CharField(max_length=255)

    class Meta:
        db_table = 'peripheral_review'


class BasicInfo(models.Model):
    name = models.CharField(max_length=100, null=True)
    lprice = models.IntegerField(null=True)
    hprice = models.IntegerField(null=True)
    image_source = models.URLField(null=True)
    link = models.CharField(max_length=255, null=True)
    brand = models.CharField(max_length=50, null=True)
    extinct = models.BooleanField(default=False)

    def __str__(self):
        return self.name

    class Meta:
        abstract = True


class Keyboard(BasicInfo):

    def __str__(self):
        return self.name

    class Meta:
        db_table = 'peripheral_keyboard'


class Printer(BasicInfo):

    def __str__(self):
        return self.name

    class Meta:
        db_table = 'peripheral_printer'


class Monitor(BasicInfo):

    def __str__(self):
        return self.name

    class Meta:
        db_table = 'peripheral_monitor'


class Mouse(BasicInfo):

    def __str__(self):
        return self.name

    class Meta:
        db_table = 'peripheral_mouse'
