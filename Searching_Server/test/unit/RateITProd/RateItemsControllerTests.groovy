package RateITProd



import org.junit.*
import grails.test.mixin.*

@TestFor(RateItemsController)
@Mock(RateItems)
class RateItemsControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/rateItems/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.rateItemsInstanceList.size() == 0
        assert model.rateItemsInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.rateItemsInstance != null
    }

    void testSave() {
        controller.save()

        assert model.rateItemsInstance != null
        assert view == '/rateItems/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/rateItems/show/1'
        assert controller.flash.message != null
        assert RateItems.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/rateItems/list'

        populateValidParams(params)
        def rateItems = new RateItems(params)

        assert rateItems.save() != null

        params.id = rateItems.id

        def model = controller.show()

        assert model.rateItemsInstance == rateItems
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/rateItems/list'

        populateValidParams(params)
        def rateItems = new RateItems(params)

        assert rateItems.save() != null

        params.id = rateItems.id

        def model = controller.edit()

        assert model.rateItemsInstance == rateItems
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/rateItems/list'

        response.reset()

        populateValidParams(params)
        def rateItems = new RateItems(params)

        assert rateItems.save() != null

        // test invalid parameters in update
        params.id = rateItems.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/rateItems/edit"
        assert model.rateItemsInstance != null

        rateItems.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/rateItems/show/$rateItems.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        rateItems.clearErrors()

        populateValidParams(params)
        params.id = rateItems.id
        params.version = -1
        controller.update()

        assert view == "/rateItems/edit"
        assert model.rateItemsInstance != null
        assert model.rateItemsInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/rateItems/list'

        response.reset()

        populateValidParams(params)
        def rateItems = new RateItems(params)

        assert rateItems.save() != null
        assert RateItems.count() == 1

        params.id = rateItems.id

        controller.delete()

        assert RateItems.count() == 0
        assert RateItems.get(rateItems.id) == null
        assert response.redirectedUrl == '/rateItems/list'
    }
}
